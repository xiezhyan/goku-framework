package top.zopx.netty.parse;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import top.zopx.netty.configurator.NettyProperties;
import top.zopx.netty.listener.ChannelInboundHandlerListener;
import top.zopx.starter.tools.exceptions.BusException;
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
public class NettyServerAcceptor extends ChannelInboundHandlerAdapter {

    /**
     * APP端端口
     */
    private final NettyProperties.App app;
    /**
     * ws端端口
     */
    private final NettyProperties.Webs webs;

    /**
     * http端端口
     */
    private final NettyProperties.Http http;
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
     * 消息处理器
     */
    private final ChannelInboundHandlerListener listener;

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
     * WS端主线程组
     */
    private EventLoopGroup httpBoss;
    /**
     * WS端工作线程组
     */
    private EventLoopGroup httpWork;

    /**
     * 主线程池方法
     */
    private final ThreadFactory bossFactory;
    /**
     * 工作线程池方法
     */
    private final ThreadFactory workFactory;

    /**
     * websocket解码编码器
     */
    private final MessageToMessageCodec<BinaryWebSocketFrame, GeneratedMessageV3> websCodec;
    /**
     * 自定义协议解码编码器
     */
    private final ByteToMessageCodec<GeneratedMessageV3> appCodec;

    public NettyServerAcceptor(Builder builder) {
        this.app = builder.app;
        this.http = builder.http;
        this.webs = builder.webs;

        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.bossThreadPool = builder.bossThreadPool;
        this.workThreadPool = builder.workThreadPool;

        this.listener = builder.listener;

        this.websCodec = builder.websCodec;
        this.appCodec = builder.appCodec;

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

    private void createHttpEventLoopGroup() {
        if (isLinux()) {
            httpBoss = new EpollEventLoopGroup(bossThreadPool, bossFactory);
            httpWork = new EpollEventLoopGroup(workThreadPool, workFactory);
        } else {
            httpBoss = new NioEventLoopGroup(bossThreadPool, bossFactory);
            httpWork = new NioEventLoopGroup(workThreadPool, workFactory);
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
//        this.destory(this.httpBoss, this.httpBoss);
    }

    /**
     * 启动服务
     */
    public void bind() {
        if (null == this.listener) {
            throw new BusException("服务处理接口异常");
        }

        if (null == app || null == appCodec) {
            throw new BusException("自定义协议服务必要参数丢失");
        }
        this.bindAppServer();

        if (null == webs || null == websCodec) {
            throw new BusException("websocket服务必要参数丢失");
        }
        this.bindWebsocketServer();

//        if (null == http) {
//            throw new BusException("Http服务必要参数丢失");
//        }
//        this.bindHttpServer();
    }

    /**
     * http服务
     */
    private void bindHttpServer() {
        createHttpEventLoopGroup();

        final ChannelFuture channelFuture = createServerBootstrap(http.getPort(), socketChannel -> {
            socketChannel.pipeline().addLast(
                    new HttpServerCodec(),
                    new HttpObjectAggregator(65535),
                    new ChunkedWriteHandler(),
                    new IdleStateHandler(readTimeout.getSeconds(), writeTimeout.getSeconds(), 0, TimeUnit.SECONDS),
                    new LoggingHandler(LogLevel.INFO),
                    NettyServerAcceptor.this
            );
        }, httpBoss, httpWork);

        channelFuture.channel()
                .newSucceededFuture()
                .addListener(future -> {
                    String log = "\n ____________________________________________\n" +
                            "|                                            |\n" +
                            "|   Http服务启动成功,端口设置：{}         |\n" +
                            "|____________________________________________|";
                    LogUtil.getInstance(NettyServerAcceptor.class).info(log, http.getPort());
                })
                .channel()
                .closeFuture()
                .addListener(future -> this.destory(this.httpBoss, this.httpWork));
    }

    /**
     * 启动WS服务
     * MessageToMessageCodec
     */
    private void bindWebsocketServer() {
        createWebSocketEventLoopGroup();

        ChannelFuture channelFuture = createServerBootstrap(webs.getPort(), ch -> {
            ch.pipeline().addLast(
                    new HttpServerCodec(),
                    new HttpObjectAggregator(65535),
                    new WebSocketServerProtocolHandler("/", false),
                    new ChunkedWriteHandler(),
                    websCodec,
                    new IdleStateHandler(readTimeout.getSeconds(), writeTimeout.getSeconds(), 0, TimeUnit.SECONDS),
                    new LoggingHandler(LogLevel.INFO),
                    NettyServerAcceptor.this
            );
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
            ch.pipeline().addLast(
                    appCodec,
                    new IdleStateHandler(readTimeout.getSeconds(), writeTimeout.getSeconds(), 0, TimeUnit.SECONDS),
                    new LoggingHandler(LogLevel.INFO),
                    NettyServerAcceptor.this
            );
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

    /**
     * 连接进来
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LogUtil.getInstance(getClass()).info("新进客户端连接进来：{}，{}", ctx.name(), ctx.channel().id());
        listener.doActive(ctx);
    }

    /**
     * 连接断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LogUtil.getInstance(getClass()).info("客户端断开连接：{}", ctx.name());
        listener.doInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取消息类
        Class<?> msgClazz = msg.getClass();

        LogUtil.getInstance(getClass()).info(
                "收到客户端消息, msgClazz = {}, msg = {}",
                msgClazz.getName(),
                msg
        );

        listener.doRead(ctx, msg);
    }

    /**
     * 事件处理
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            listener.doIdleStateEvent(ctx, (IdleStateEvent) evt);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 异常信息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LogUtil.getInstance(getClass()).error("异常信息：{}", cause.getMessage());
        listener.doException(ctx, cause);
    }

    public static class Builder {
        NettyProperties.App app;
        NettyProperties.Http http;
        NettyProperties.Webs webs;
        private Duration readTimeout;
        private Duration writeTimeout;
        private int bossThreadPool;
        private int workThreadPool;
        private ChannelInboundHandlerListener listener;

        /**
         * websocket解码编码器
         */
        MessageToMessageCodec<BinaryWebSocketFrame, GeneratedMessageV3> websCodec;
        /**
         * 自定义协议解码编码器
         */
        ByteToMessageCodec<GeneratedMessageV3> appCodec;

        public Builder setApp(NettyProperties.App app) {
            this.app = app;
            return this;
        }

        public Builder setHttp(NettyProperties.Http http) {
            this.http = http;
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

        public Builder setListener(ChannelInboundHandlerListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setWebsCodec(MessageToMessageCodec<BinaryWebSocketFrame, GeneratedMessageV3> websCodec) {
            this.websCodec = websCodec;
            return this;
        }

        public Builder setAppCodec(ByteToMessageCodec<GeneratedMessageV3> appCodec) {
            this.appCodec = appCodec;
            return this;
        }

        public NettyServerAcceptor build() {
            return new NettyServerAcceptor(this);
        }
    }
}
