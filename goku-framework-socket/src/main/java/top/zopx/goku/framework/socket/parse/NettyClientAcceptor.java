package top.zopx.goku.framework.socket.parse;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.handle.BaseChannelHandlerFactory;
import top.zopx.goku.framework.tools.exceptions.BusException;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 常用于网关和业务服务间的处理
 *
 * @author 俗世游子
 * @date 2022/2/3
 * @email xiezhyan@126.com
 */
public final class NettyClientAcceptor {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientAcceptor.class);

    private static final ThreadFactory THREAD_FACTORY = r -> new Thread(r, "goku-websocket-client-work");

    private static final EpollEventLoopGroup EPOLL_EVENT_LOOP_GROUP = new EpollEventLoopGroup(THREAD_FACTORY);
    private static final NioEventLoopGroup NIO_EVENT_LOOP_GROUP = new NioEventLoopGroup(THREAD_FACTORY);

    /**
     * 使用配置
     */
    private final ConnectClient client;

    /**
     * 额外信息字典
     */
    private Map<String, String> extraInfoMap;

    /**
     * 客户端信道
     */
    private Channel channel;

    /**
     * 已就绪
     */
    private boolean ready = false;

    /**
     * 类参数构造器
     *
     * @param client 使用配置
     */
    public NettyClientAcceptor(ConnectClient client) {
        if (null == client) {
            throw new BusException("usingConf is null");
        }

        this.client = client;
    }

    /**
     * 设置附加信息
     *
     * @param key 关键字
     * @param val 字符串值
     */
    public void putExtraInfo(String key, String val) {
        if (null == key) {
            return;
        }

        if (null == val &&
                null == extraInfoMap) {
            return;
        }

        if (null == extraInfoMap) {
            extraInfoMap = new ConcurrentHashMap<>();
        }

        extraInfoMap.put(key, val);
    }

    public boolean isLinux() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux");
    }

    /**
     * 连接到服务器端
     */
    public void connect() {
        connect(null);
    }

    /**
     * 连接到服务器端
     *
     * @param work 自定义 EventLoopGroup
     */
    public void connect(EventLoopGroup work) {
        try {
            final URI serverURI = new URI(MessageFormat.format(
                    "{0}://{1}:{2}/{3}",
                    client.getWsPrefix(),
                    client.getServerHost(),
                    String.valueOf(client.getServerPort()),
                    client.getPath()
            ));

            final DefaultHttpHeaders headerz = new DefaultHttpHeaders();

            if (null != extraInfoMap) {
                for (Map.Entry<String, String> entry : extraInfoMap.entrySet()) {
                    if (null != entry.getValue()) {
                        headerz.add(
                                entry.getKey(), entry.getValue()
                        );
                    }
                }
            }

            final WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                    serverURI,
                    WebSocketVersion.V13,
                    null,
                    true,
                    headerz
            );

            Bootstrap b = new Bootstrap();
            b.group(
                    null == work ?
                            isLinux() ?
                                    EPOLL_EVENT_LOOP_GROUP :
                                    NIO_EVENT_LOOP_GROUP :
                            work
            );

            b.channel(
                    null == work ?
                            isLinux() ?
                                    EpollSocketChannel.class :
                                    NioSocketChannel.class :
                            work instanceof EpollEventLoopGroup ?
                                    EpollSocketChannel.class :
                                    NioSocketChannel.class
            );
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    // 获取信道处理器工厂
                    final BaseChannelHandlerFactory f = client.getChannelHandlerFactory();
                    // 消息处理器
                    ChannelHandler msgHandler = null;

                    if (null != f) {
                        msgHandler = f.createWSMsgHandler();
                    }

                    // 如果是WS的形式，那么才会加入这些
                    if (client.getServerType() == ConnectClient.WS) {
                        ChannelHandler[] hArray = {
                                new HttpClientCodec(), // Http 客户端编解码器
                                new HttpObjectAggregator(65535), // 内容长度限制
                                new WebSocketClientProtocolHandler(handshaker), // WebSocket 协议处理器, 在这里处理握手、ping、pong 等消息
                                msgHandler, // 消息处理器
                        };

                        for (ChannelHandler h : hArray) {
                            if (null != h) {
                                ch.pipeline().addLast(h);
                            }
                        }
                    } else {
                        ch.pipeline().addLast(msgHandler);
                    }
                }
            });
            b.option(ChannelOption.SO_KEEPALIVE, true);

            // 客户端开启
            ChannelFuture f = b.connect(
                    client.getServerHost(),
                    client.getServerPort()
            ).sync();

            if (!f.isSuccess()) {
                return;
            }

            channel = f.channel();
            channel.closeFuture().addListener(x -> {
                // 获取已关闭的客户端
                NettyClientAcceptor closeClient = NettyClientAcceptor.this;

                LOGGER.warn(
                        "XXX 注意: 服务器连接关闭! {} XXX",
                        client
                );

                ready = false;
                closeClient.channel = null;

                // 获取并执行关闭回调函数
                final ConnectClient.ICloseCallback callback = client.getCloseCallback();

                if (null != callback) {
                    callback.apply(closeClient);
                }
            });

            // 只有是WS的形式，才需要升级握手
            if (client.getServerType() == ConnectClient.WS) {
                // 用 CD 来等待握手
                final CountDownLatch cdL = new CountDownLatch(32);

                while (cdL.getCount() > 0 &&
                        !handshaker.isHandshakeComplete()) {
                    // 在这里等待握手成功
                    cdL.await(200, TimeUnit.MILLISECONDS);
                    cdL.countDown();
                }

                if (!handshaker.isHandshakeComplete()) {
                    return;
                }
            }
            LOGGER.info(
                    ">>> 连接到服务器成功! {} <<<",
                    client
            );
            // 准备完成
            ready = true;
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    /**
     * 发送消息
     *
     * @param msgObj 消息对象
     */
    public void sendMsg(Object msgObj) {
        if (!isReady()) {
            LOGGER.error("客户端未准备好");
            return;
        }

        if (null == channel) {
            LOGGER.error("client channel is null");
            return;
        }

        channel.writeAndFlush(msgObj);
    }

    /**
     * 获取服务器 Id
     *
     * @return 服务器 Id
     */
    public int getServerId() {
        return client.getServerId();
    }

    /**
     * 获取服务器名称
     *
     * @return 服务器名称
     */
    public String getServerName() {
        return client.getServerName();
    }

    /**
     * 获取服务器工作类型数组
     *
     * @return 服务器工作类型数组
     */
    public Set<String> getServerJobTypeSet() {
        return client.getServerJobTypeSet();
    }

    /**
     * 获取服务器主机地址
     *
     * @return 服务器主机地址
     */
    public String getServerHost() {
        return client.getServerHost();
    }

    /**
     * 获取服务器端口号
     *
     * @return 服务器端口号
     */
    public int getServerPort() {
        return client.getServerPort();
    }

    /**
     * 是否准备好
     *
     * @return true = 已准备好, false = 未准备好
     */
    public boolean isReady() {
        return ready;
    }
}
