package top.zopx.square.netty.configurator.parse;

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
import top.zopx.square.netty.configurator.properties.NettyServerConfig;
import top.zopx.square.netty.handle.BaseChannelHandlerFactory;

import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Server处理工具
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
    private final NettyServerConfig.App app;
    /**
     * ws端端口
     */
    private final NettyServerConfig.Ws ws;

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

    private NettyServerAcceptor(Builder builder) {
        this.app = builder.app;
        this.ws = builder.ws;

        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.bossThreadPool = builder.bossThreadPool;
        this.workThreadPool = builder.workThreadPool;

        this.factory = builder.factory;

        bossFactory = r -> new Thread(r, "square-boss");
        workFactory = r -> new Thread(r, "square-work");
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
    public void destory(EventLoopGroup boss, EventLoopGroup work) {
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
    public void destory() {
        this.destory(this.appBoss, this.appWork);
        this.destory(this.websocketBoss, this.websocketWork);
    }

    /**
     * 启动服务
     */
    public void start() {
        if (null == factory) {
            throw new RuntimeException("HandleFactory处理器异常");
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
     * MessageToMessageCodec
     */
    private void bindWebsocketServer() {
        createWebSocketEventLoopGroup();

        String path = StringUtils.isBlank(ws.getPath()) ? "/ws" : ws.getPath();
        ChannelFuture channelFuture = createServerBootstrap(ws.getHost(), ws.getPort(), ch -> {
            LOGGER.info("WS Server init Handler");

            final ChannelHandler msgHandler = factory.createWSMsgHandler();

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
                            "                                                                   \n" +
                            "   WebSocket服务启动成功,绑定设置：【ws://{}:{}{}】                                 \n" +
                            "_____________________________________________________________________";
                    LOGGER.info(log, ws.getHost(), ws.getPort(), path);
                });
        serverChannel
                .closeFuture()
                .addListener(future -> this.destory(this.websocketBoss, this.websocketWork));
    }

    /**
     * 启动APP端服务
     * ByteToMessageCodec
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
                            "                                                                 \n" +
                            "   App服务启动成功,绑定地址：【{}:{}】                                  \n" +
                            "_____________________________________________________________________";
                    LOGGER.info(log, app.getHost(), app.getPort());
                });
        serverChannel
                .closeFuture()
                .addListener(future -> this.destory(this.appBoss, this.appWork));
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

    public boolean isLinux() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux");
    }

    public static class Builder {
        NettyServerConfig.App app;
        NettyServerConfig.Ws ws;
        private Duration readTimeout;
        private Duration writeTimeout;
        private int bossThreadPool;
        private int workThreadPool;

        private BaseChannelHandlerFactory factory;

        public Builder setApp(NettyServerConfig.App app) {
            this.app = app;
            return this;
        }

        public Builder setWs(NettyServerConfig.Ws ws) {
            this.ws = ws;
            return this;
        }

        public Builder setReadTimeout(Duration readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(Duration writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setBossThreadPool(int bossThreadPool) {
            this.bossThreadPool = bossThreadPool;
            return this;
        }

        public Builder setWorkThreadPool(int workThreadPool) {
            this.workThreadPool = workThreadPool;
            return this;
        }

        public Builder setFactory(BaseChannelHandlerFactory factory) {
            this.factory = factory;
            return this;
        }

        public NettyServerAcceptor build() {
            return new NettyServerAcceptor(this);
        }
    }
}
