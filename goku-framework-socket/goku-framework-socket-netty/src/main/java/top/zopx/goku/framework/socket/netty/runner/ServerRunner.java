package top.zopx.goku.framework.socket.netty.runner;

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
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.netty.handle.IChannelHandle;
import top.zopx.goku.framework.socket.netty.properties.App;
import top.zopx.goku.framework.socket.netty.properties.Server;
import top.zopx.goku.framework.socket.netty.properties.Websocket;
import top.zopx.goku.framework.socket.netty.util.Util;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Server处理工具 启动类
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2021/9/10
 */
public class ServerRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerRunner.class);

    /**
     * APP端端口
     */
    private final App app;
    /**
     * ws端
     */
    private final Websocket ws;

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

    private final IChannelHandle channelHandle;

    public ServerRunner(Server server) {
        this.server = server;
        this.app = server.getApp();
        this.ws = server.getWebsocket();

        this.channelHandle = server.getChannelHandle();
    }

    /**
     * APP端根据系统验证创建那种类型的线程组
     */
    private void createAppEventLoopGroup() {
        if (Util.isLinux()) {
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
        if (Util.isLinux()) {
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
        if (Objects.isNull(this.channelHandle)) {
            LOGGER.error("channel handle is null");
            return;
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
            LOGGER.info("WS Server init");

            final ChannelHandler msgHandler = channelHandle.createWebsocketMsgHandler();

            ChannelHandler[] handlers = {
                    // http支持
                    new HttpServerCodec(),
                    new HttpObjectAggregator(65535),
                    // 应答数据压缩
                    new WebSocketServerCompressionHandler(),
                    // websocket支持
                    new WebSocketServerProtocolHandler(path, null, true),
                    // 大型数据流
                    new ChunkedWriteHandler(),
                    new IdleStateHandler(server.getReadTimeout().getSeconds(), server.getWriteTimeout().getSeconds(), 0, TimeUnit.SECONDS),
                    new HeartBeatHandler(),
                    new LoggingHandler(LogLevel.valueOf(server.getLogLevel().toUpperCase())),
                    msgHandler,
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
                    String log = """
                            ﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊
                                    WebSocket服务启动成功,绑定设置：【{}】
                            ﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎
                            """;
                    LOGGER.info(log, ws.getInitialWebsocketPath());
                });
        serverChannel
                .closeFuture()
                .addListener(future -> this.destroy(this.websocketBoss, this.websocketWork));
    }

    static class HeartBeatHandler extends ChannelDuplexHandler {

        private static final AtomicInteger COUNT = new AtomicInteger(0);

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent && COUNT.getAndIncrement() >= 3) {
                LOGGER.warn("~~~{} 未发送数据，将强制下线~~~", ctx.channel().remoteAddress());
                ctx.channel().disconnect().sync().await(2, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * 启动APP端服务
     */
    private void bindAppServer() {
        createAppEventLoopGroup();

        ChannelFuture channelFuture = createServerBootstrap(app.getHost(), app.getPort(), ch -> {
            LOGGER.info("App Server init");

            final ChannelHandler msgHandler = channelHandle.createAppMsgHandler();

            ChannelHandler[] handlers = {
                    msgHandler, // 消息处理器
                    new IdleStateHandler(server.getReadTimeout().getSeconds(), server.getWriteTimeout().getSeconds(), 0, TimeUnit.SECONDS),
                    new HeartBeatHandler(),
                    new LoggingHandler(LogLevel.valueOf(server.getLogLevel().toUpperCase())),
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
                    String log = """
                            ﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊
                                App服务启动成功,绑定地址：【{}:{}】
                            ﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎
                            """;
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

        buildServerOption(serverBootstrap);

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
    public void buildServerOption(ServerBootstrap serverBootstrap) {
        serverBootstrap
                // 客户端队列备胎 超过内核状态syn_rece --> netstat -natp
                .option(ChannelOption.SO_BACKLOG, Runtime.getRuntime().availableProcessors() * 500)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
    }

    private Class<? extends ServerChannel> getServerChannel() {
        return Util.isLinux() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }
}
