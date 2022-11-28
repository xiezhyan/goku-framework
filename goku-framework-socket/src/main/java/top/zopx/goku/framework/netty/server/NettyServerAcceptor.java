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
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.netty.bind.entity.AppClient;
import top.zopx.goku.framework.netty.bind.entity.WebsocketClient;
import top.zopx.goku.framework.netty.bind.factory.BaseChannelHandlerFactory;
import top.zopx.goku.framework.tools.exceptions.BusException;

import java.time.Duration;
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
public class NettyServerAcceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerAcceptor.class);

    /**
     * APP端端口
     */
    private final AppClient app;
    /**
     * ws端端口
     */
    private final WebsocketClient ws;

    /**
     * 读操作超时时间
     */
    private final Duration readTimeout;
    /**
     * 写操作超时时间
     */
    private final Duration writeTimeout;
    /**
     * 主线程数
     */
    private final int bossThreadPool;
    /**
     * 工作线程数
     */
    private final int workThreadPool;

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
    private final ThreadFactory bossFactory;
    /**
     * 工作线程池方法
     */
    private final ThreadFactory workFactory;

    private final BaseChannelHandlerFactory factory;

    public NettyServerAcceptor(ServerAcceptor server) {
        this.app = server.getApp();
        this.ws = server.getWs();

        this.readTimeout = server.getReadTimeout();
        this.writeTimeout = server.getWriteTimeout();
        this.bossThreadPool = server.getBossThreadPool();
        this.workThreadPool = server.getWorkThreadPool();

        this.factory = server.getFactory();

        bossFactory = r -> new Thread(r, "goku-socket-boss");
        workFactory = r -> new Thread(r, "goku-socket-work");
    }

    /**
     * APP端根据系统验证创建那种类型的线程组
     */
    private void createAppEventLoopGroup() {
        if (isLinux()) {
            appBoss = new EpollEventLoopGroup(bossThreadPool, bossFactory);
            appWork = new EpollEventLoopGroup(workThreadPool, workFactory);
        } else {
            appBoss = new NioEventLoopGroup(bossThreadPool, bossFactory);
            appWork = new NioEventLoopGroup(workThreadPool, workFactory);
        }
    }

    /**
     * WS端根据系统验证创建那种类型的线程组
     */
    private void createWebSocketEventLoopGroup() {
        if (isLinux()) {
            websocketBoss = new EpollEventLoopGroup(bossThreadPool, bossFactory);
            websocketWork = new EpollEventLoopGroup(workThreadPool, workFactory);
        } else {
            websocketBoss = new NioEventLoopGroup(bossThreadPool, bossFactory);
            websocketWork = new NioEventLoopGroup(workThreadPool, workFactory);
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
            throw new BusException("BaseChannelHandlerFactory is null ");
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
                    new HttpServerCodec(),
                    new HttpObjectAggregator(65535),
                    new WebSocketServerProtocolHandler(path, false),
                    new ChunkedWriteHandler(),
                    msgHandler,
                    new IdleStateHandler(readTimeout.getSeconds(), writeTimeout.getSeconds(), 0, TimeUnit.SECONDS),
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
                    new IdleStateHandler(readTimeout.getSeconds(), writeTimeout.getSeconds(), 0, TimeUnit.SECONDS),
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
        return new ServerBootstrap()
                .group(boss, work)
                .channel(this.getServerChannel())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        consumer.accept(ch);
                    }
                })
                .bind(host, port)
                .syncUninterruptibly();
    }

    private Class<? extends ServerChannel> getServerChannel() {
        return isLinux() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }

    private boolean isLinux() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux");
    }
}
