package top.zopx.netty.parse;

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
import top.zopx.netty.configurator.NettyProperties;
import top.zopx.netty.listener.BaseChannelHandlerFactory;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.strings.StringUtil;
import top.zopx.starter.tools.tools.web.LogUtil;

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
@ChannelHandler.Sharable
public class NettyServerAcceptor {

    /**
     * APP端端口
     */
    private final NettyProperties.App app;
    /**
     * ws端端口
     */
    private final NettyProperties.Webs webs;

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

    public NettyServerAcceptor(Builder builder) {
        this.app = builder.app;
        this.webs = builder.webs;

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
    public void bind() {
        if (null == factory) {
            throw new BusException("HandleFactory处理器异常");
        }

        if (null != app) {
            this.bindAppServer();
        }

        if (null != webs) {
            this.bindWebsocketServer();
        }
    }

    /**
     * 启动WS服务
     * MessageToMessageCodec
     */
    private void bindWebsocketServer() {
        createWebSocketEventLoopGroup();

        ChannelFuture channelFuture = createServerBootstrap(webs.getPort(), ch -> {
            LogUtil.getInstance(NettyServerAcceptor.class).info("WS Server init Handler");

            final ChannelHandler msgHandler = factory.createWSMsgHandler();

            ChannelHandler[] handlers = {
                    new HttpServerCodec(),
                    new HttpObjectAggregator(65535),
                    new WebSocketServerProtocolHandler(StringUtil.isBlank(webs.getWsPath()) ? "/" : webs.getWsPath(), false),
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

        channelFuture.channel()
                .newSucceededFuture()
                .addListener(future -> {
                    String log = "\n ____________________________________________\n" +
                            "|                                            |\n" +
                            "|   WebSocket服务启动成功,端口设置：{}      |\n" +
                            "|____________________________________________|";
                    LogUtil.getInstance(NettyServerAcceptor.class).info(log, webs.getPort());
                })
                .channel()
                .closeFuture()
                .addListener(future -> this.destory(this.websocketBoss, this.websocketWork));
    }

    /**
     * 启动APP端服务
     * ByteToMessageCodec
     */
    private void bindAppServer() {
        createAppEventLoopGroup();

        ChannelFuture channelFuture = createServerBootstrap(app.getPort(), ch -> {
            LogUtil.getInstance(NettyServerAcceptor.class).info("App Server init Handler");

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

        channelFuture
                .channel()
                .newSucceededFuture()
                .addListener(future -> {
                    String log = "\n ______________________________________\n" +
                            "|                                      |\n" +
                            "|   App服务启动成功,端口设置：{}      |\n" +
                            "|______________________________________|";
                    LogUtil.getInstance(NettyServerAcceptor.class).info(log, app.getPort());
                })
                .channel()
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
    private ChannelFuture createServerBootstrap(int port, Consumer<SocketChannel> consumer, EventLoopGroup boss, EventLoopGroup work) {
        return new ServerBootstrap()
                .group(boss, work)
                .channel(this.getServerChannel())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        consumer.accept(ch);
                    }
                })
                .bind(port)
                .syncUninterruptibly();
    }

    private Class<? extends ServerChannel> getServerChannel() {
        return isLinux() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }

    private boolean isLinux() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux");
    }

    public static class Builder {
        NettyProperties.App app;
        NettyProperties.Webs webs;
        private Duration readTimeout;
        private Duration writeTimeout;
        private int bossThreadPool;
        private int workThreadPool;

        private BaseChannelHandlerFactory factory;

        public Builder setApp(NettyProperties.App app) {
            this.app = app;
            return this;
        }

        public Builder setWebs(NettyProperties.Webs webs) {
            this.webs = webs;
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
