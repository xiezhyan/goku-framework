package top.zopx.goku.framework.socket.core.selector.connect;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.core.cmd.ISocketBusHandle;
import top.zopx.goku.framework.socket.core.util.Util;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 服务器间互相链接
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/23
 */
public class MultiServerRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiServerRunner.class);

    private static final ThreadFactory THREAD_FACTORY = r -> new Thread(r, "goku-websocket-client-work");

    private final Map<String, String> extraMap;

    private final MultiServer server;

    /**
     * 客户端信道
     */
    private Channel channel;

    private Boolean isReady = Boolean.FALSE;

    public MultiServerRunner(MultiServer server, String... args) {
        this.server = server;
        extraMap = new ConcurrentHashMap<>(args.length);
        for (int i = 0; i < args.length; i += 2) {
            putExtra(args[i], args[i + 1]);
        }
    }

    public void putExtra(String key, String value) {
        if (StringUtils.isBlank(key) || Objects.isNull(value)) {
            return;
        }
        extraMap.put(key, value);
    }

    public void connect() {
        connect(
                Util.isLinux() ?
                        new EpollEventLoopGroup(THREAD_FACTORY) : new NioEventLoopGroup(THREAD_FACTORY)
        );
    }

    private void connect(EventLoopGroup eventLoopGroup) {
        connectWebsocket(eventLoopGroup);
    }

    private void connectWebsocket(EventLoopGroup work) {
        if (!MultiServer.ServerEnum.WEB_SOCKET.equals(server.getServerType())) {
            return;
        }

        try {
            final URI serverUri = new URI(server.getWebsocketPath());
            final DefaultHttpHeaders header = new DefaultHttpHeaders();
            extraMap.forEach(header::add);

            final WebSocketClientHandshaker websocketHandShaker = WebSocketClientHandshakerFactory.newHandshaker(
                    serverUri,
                    WebSocketVersion.V13,
                    null,
                    true,
                    header
            );

            ChannelFuture f =
                    new Bootstrap()
                            .group(work)
                            .channel(
                                    work instanceof EpollEventLoopGroup ?
                                            EpollSocketChannel.class :
                                            NioSocketChannel.class
                            ).handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) {
                                    // 获取信道处理器工厂
                                    ISocketBusHandle channelHandle = server.getChannelHandle();
                                    // 消息处理器
                                    ChannelHandler msgHandler = null;

                                    if (null != channelHandle) {
                                        msgHandler = channelHandle.createWebsocketMsgHandler();
                                    }

                                    ChannelHandler[] hArray = {
                                            new HttpClientCodec(), // Http 客户端编解码器
                                            new HttpObjectAggregator(65535), // 内容长度限制
                                            new WebSocketClientProtocolHandler(websocketHandShaker), // WebSocket 协议处理器, 在这里处理握手、ping、pong 等消息
                                            msgHandler, // 消息处理器
                                    };

                                    for (ChannelHandler h : hArray) {
                                        ch.pipeline().addLast(h);
                                    }
                                }
                            })
                            .option(ChannelOption.SO_KEEPALIVE, true)
                            .connect(
                                    server.getServerHost(),
                                    server.getServerPort()
                            ).sync();

            if (!f.isSuccess()) {
                return;
            }

            channel = f.channel();
            channel.closeFuture().addListener(x -> {
                // 获取已关闭的客户端
                MultiServerRunner closeClient = MultiServerRunner.this;
                LOGGER.warn(
                        "XXX 注意: 服务器连接关闭! {} XXX",
                        server
                );
                isReady = Boolean.FALSE;
                closeClient.channel = null;
                // 获取并执行关闭回调函数
                final MultiServer.ICloseCallback callback = server.getCloseCallback();
                if (null != callback) {
                    callback.apply(closeClient);
                }
            });

            // 只有是WS的形式，才需要升级握手, 用 CD 来等待握手
            final CountDownLatch cdL = new CountDownLatch(32);
            while (cdL.getCount() > 0 &&
                    !websocketHandShaker.isHandshakeComplete()) {
                // 在这里等待握手成功
                if (cdL.await(200, TimeUnit.MILLISECONDS)) {
                    cdL.countDown();
                }
            }
            if (!websocketHandShaker.isHandshakeComplete()) {
                return;
            }
            LOGGER.info(
                    ">>> 连接到服务器成功! {} <<<",
                    server
            );
            // 准备完成
            isReady = true;
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
            Thread.currentThread().interrupt();
        }
    }

    public void send(Object msg) {
        if (Boolean.FALSE.equals(getReady())) {
            LOGGER.error("客户端未准备好");
            return;
        }

        if (null == channel) {
            LOGGER.error("client channel is null");
            return;
        }

        if (Objects.isNull(msg)) {
            return;
        }

        channel.writeAndFlush(msg);
    }

    /**
     * 获取服务器 Id
     *
     * @return 服务器 Id
     */
    public int getServerId() {
        return server.getServerId();
    }

    /**
     * 获取服务器名称
     *
     * @return 服务器名称
     */
    public String getServerName() {
        return server.getServerName();
    }

    /**
     * 获取服务器工作类型数组
     *
     * @return 服务器工作类型数组
     */
    public Set<String> getServerJobTypeSet() {
        return server.getServerJobTypeSet();
    }

    /**
     * 获取服务器主机地址
     *
     * @return 服务器主机地址
     */
    public String getServerHost() {
        return server.getServerHost();
    }

    /**
     * 获取服务器端口号
     *
     * @return 服务器端口号
     */
    public int getServerPort() {
        return server.getServerPort();
    }

    public Boolean getReady() {
        return isReady;
    }
}
