package top.zopx.goku.framework.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.netty.bind.entity.AppClient;
import top.zopx.goku.framework.netty.bind.entity.Server;
import top.zopx.goku.framework.netty.bind.entity.WebsocketClient;
import top.zopx.goku.framework.netty.bind.factory.BaseChannelHandlerFactory;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.exceptions.IBus;

import java.util.Locale;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Server处理工具 启动类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/9/10
 */
public class ServerActuator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerActuator.class);

    /**
     * APP端端口
     */
    private final AppClient app;
    /**
     * ws端
     */
    private final WebsocketClient ws;

    /**
     * 系统参数
     */
    private final Server server;

    /**
     * APP端主线程组
     */
    private EventLoopGroup appBoss;
    /**
     * APP端工作线程组
     */
    private EventLoopGroup appWork;
    /**
     * WS端主线程组
     */
    private EventLoopGroup websocketBoss;
    /**
     * WS端工作线程组
     */
    private EventLoopGroup websocketWork;

    /**
     * 主线程池方法
     */
    private final ThreadFactory bossFactory = r -> new Thread(r, "goku-socket-boss");
    /**
     * 工作线程池方法
     */
    private final ThreadFactory workFactory = r -> new Thread(r, "goku-socket-work");

    private final BaseChannelHandlerFactory factory;

    public ServerActuator(Server server) {
        this.server = server;
        this.app = server.getApp();
        this.ws = server.getWs();

        this.factory = server.getFactory();
    }

    /**
     * APP端根据系统验证创建那种类型的线程组
     */
    private void createAppEventLoopGroup() {
        if (isLinux()) {
            appBoss = new EpollEventLoopGroup(server.getBossThreadPool(), bossFactory);
            appWork = new EpollEventLoopGroup(server.getWorkThreadPool(), workFactory);
        } else {
            appBoss = new NioEventLoopGroup(server.getBossThreadPool(), bossFactory);
            appWork = new NioEventLoopGroup(server.getWorkThreadPool(), workFactory);
        }
    }

    /**
     * WS端根据系统验证创建那种类型的线程组
     */
    private void createWebSocketEventLoopGroup() {
        if (isLinux()) {
            websocketBoss = new EpollEventLoopGroup(server.getBossThreadPool(), bossFactory);
            websocketWork = new EpollEventLoopGroup(server.getWorkThreadPool(), workFactory);
        } else {
            websocketBoss = new NioEventLoopGroup(server.getBossThreadPool(), bossFactory);
            websocketWork = new NioEventLoopGroup(server.getWorkThreadPool(), workFactory);
        }
    }

    /**
     * 优雅停机
     *
     * @param boss 主线程组
     * @param work 工作线程组
     */
    private void destroy(EventLoopGroup boss, EventLoopGroup work) {
        if (null != boss && !boss.isShutdown() && !boss.isShuttingDown()) {
            boss.shutdownGracefully();
        }
        if (null != work && !work.isShutdown() && !work.isShuttingDown()) {
            work.shutdownGracefully();
        }
    }

    /**
     * 服务停止之后执行该方法
     */
    public void destroy() {
        this.destroy(this.appBoss, this.appWork);
        this.destroy(this.websocketBoss, this.websocketWork);
    }

    /**
     * 启动服务
     */
    public void start() {
        if (null == factory) {
            throw new BusException("BaseChannelHandlerFactory is null", IBus.ERROR_CODE, "");
        }

        if (null != app) {
            this.bindAppServer();
        }

        if (null != ws) {
            this.bindWebsocketServer();
        }
    }

    /**
     * 启动WS服务
     */
    private void bindWebsocketServer() {
        createWebSocketEventLoopGroup();

        String path = StringUtils.isBlank(ws.getPath()) ? "/ws" : ws.getPath();
        ChannelFuture channelFuture = createServerBootstrap(ws.getHost(), ws.getPort(), ch -> {
            LOGGER.info("WS Server init Handler");

            final ChannelHandler msgHandler = factory.createWebsocketMsgHandler();

            ChannelHandler[] handlers = {
                    // http支持
                    new HttpServerCodec(),
                    new HttpObjectAggregator(65535),
                    // 应答数据压缩
                    new WebSocketServerCompressionHandler(),
                    // websocket支持
                    new WebSocketServerProtocolHandler(path, false),
                    // 大型数据流
                    new ChunkedWriteHandler(),
                    msgHandler,
                    new IdleStateHandler(server.getReadTimeout().getSeconds(), server.getWriteTimeout().getSeconds(), 0, TimeUnit.SECONDS),
                    new LoggingHandler(LogLevel.INFO),
            };

            for (ChannelHandler h : handlers) {
                if (null != h) {
                    ch.pipeline().addLast(h);
                }
            }

        }, websocketBoss, websocketWork);

        final Channel serverChannel = channelFuture.channel();

        serverChannel
                .newSucceededFuture()
                .addListener(future -> {
                    String log =
                            "\n_____________________________________________________________________\n" +
                                    "   WebSocket服务启动成功,绑定设置：【{}】                                 \n" +
                                    "_____________________________________________________________________";
                    LOGGER.info(log, ws.getShowPath());
                });
        serverChannel
                .closeFuture()
                .addListener(future -> this.destroy(this.websocketBoss, this.websocketWork));
    }

    /**
     * 启动APP端服务
     */
    private void bindAppServer() {
        createAppEventLoopGroup();

        ChannelFuture channelFuture = createServerBootstrap(app.getHost(), app.getPort(), ch -> {
            LOGGER.info("App Server init Handler");

            final ChannelHandler msgHandler = factory.createAppMsgHandler();

            ChannelHandler[] handlers = {
                    msgHandler, // 消息处理器
                    new IdleStateHandler(server.getReadTimeout().getSeconds(), server.getWriteTimeout().getSeconds(), 0, TimeUnit.SECONDS),
                    new LoggingHandler(LogLevel.INFO)
            };

            for (ChannelHandler h : handlers) {
                if (null != h) {
                    ch.pipeline().addLast(h);
                }
            }
        }, appBoss, appWork);

        final Channel serverChannel = channelFuture
                .channel();
        serverChannel
                .newSucceededFuture()
                .addListener(future -> {
                    String log =
                            "\n_____________________________________________________________________\n" +
                                    "   App服务启动成功,绑定地址：【{}:{}】                                  \n" +
                                    "_____________________________________________________________________";
                    LOGGER.info(log, app.getHost(), app.getPort());
                });
        serverChannel
                .closeFuture()
                .addListener(future -> this.destroy(this.appBoss, this.appWork));
    }

    /**
     * 创建ServerBootstrap()对象
     *
     * @param port     端口
     * @param consumer 后续操作
     * @param boss     主线程组
     * @param work     工作线程组
     * @return ChannelFuture
     */
    private ChannelFuture createServerBootstrap(String host, int port, Consumer<SocketChannel> consumer, EventLoopGroup boss, EventLoopGroup work) {
        final ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(boss, work)
                .channel(this.getServerChannel());

        updateOption(serverBootstrap);

        return serverBootstrap
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        consumer.accept(ch);
                    }
                })
                .bind(host, port)
                .syncUninterruptibly();
    }

    /**
     * 可重置服务端配置 模板方法
     *
     * @param serverBootstrap serverBootstrap
     */
    public void updateOption(ServerBootstrap serverBootstrap) {
        serverBootstrap
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
    }

    private Class<? extends ServerChannel> getServerChannel() {
        return isLinux() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }

    private boolean isLinux() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux");
    }
}
