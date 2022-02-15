package top.zopx.square.netty.configurator.parse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * NettyClient.Config clientConf = NettyClient.Config.fromJSONObj(joServerInfo);
 * clientConf.setChannelHandlerFactory(new ChannelHandlerFactoryImpl_0());
 * clientConf.setCloseCallback((closeClient) -> {
 * // Client掉线之后的操作
 * });
 * <p>
 * NettyClient serverConn = new NettyClient(clientConf);
 * serverConn.conn();
 * <p>
 * if (!serverConn.isReady()) {
 * return null;
 * }
 *
 * @author 俗世游子
 * @date 2022/2/3
 * @email xiezhyan@126.com
 */
public final class NettyClientAcceptor {
    /**
     * 日志对象
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(NettyClientAcceptor.class);

    private static final ThreadFactory THREAD_FACTORY = (r) -> new Thread(r, "square-client-work");

    private static final EpollEventLoopGroup EPOLL_EVENT_LOOP_GROUP = new EpollEventLoopGroup(THREAD_FACTORY);

    private static final NioEventLoopGroup NIO_EVENT_LOOP_GROUP = new NioEventLoopGroup(THREAD_FACTORY);

    private static final Gson GSON = new Gson();
    /**
     * 使用配置
     */
    private final Config _usingConf;

    /**
     * 额外信息字典
     */
    private Map<String, String> _extraInfoMap;

    /**
     * 客户端信道
     */
    private Channel _ch;

    /**
     * 已就绪
     */
    private boolean _ready = false;

    /**
     * 类参数构造器
     *
     * @param usingConf 使用配置
     * @throws IllegalArgumentException if null == usingConf
     */
    public NettyClientAcceptor(Config usingConf) {
        if (null == usingConf) {
            throw new IllegalArgumentException("usingConf is null");
        }

        _usingConf = usingConf;
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
                null == _extraInfoMap) {
            return;
        }

        if (null == _extraInfoMap) {
            _extraInfoMap = new ConcurrentHashMap<>();
        }

        _extraInfoMap.put(key, val);
    }

    public boolean isLinux() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux");
    }

    /**
     * 连接到服务器端
     */
    public void conn() {
        conn(null);
    }

    /**
     * 连接到服务器端
     *
     * @param work 自定义 EventLoopGroup
     */
    public void conn(EventLoopGroup work) {
        try {
            final URI serverURI = new URI(MessageFormat.format(
                    "{0}://{1}:{2}/{3}",
                    _usingConf.getWsPrefix(),
                    _usingConf.getServerHost(),
                    String.valueOf(_usingConf.getServerPort()),
                    _usingConf.getPath()
            ));

            final DefaultHttpHeaders headerz = new DefaultHttpHeaders();

            if (null != _extraInfoMap) {
                for (Map.Entry<String, String> entry : _extraInfoMap.entrySet()) {
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
                    final Config.AbstractChannelHandlerFactory f = _usingConf.getChannelHandlerFactory();
                    // 消息处理器
                    ChannelHandler msgHandler = null;

                    if (null != f) {
                        msgHandler = f.createMsgHandler();
                    }

                    // 如果是WS的形式，那么才会加入这些
                    if (_usingConf.getServerType() == Config.WS) {
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
                    _usingConf.getServerHost(),
                    _usingConf.getServerPort()
            ).sync();

            if (!f.isSuccess()) {
                return;
            }

            _ch = f.channel();
            _ch.closeFuture().addListener((x) -> {
                // 获取已关闭的客户端
                NettyClientAcceptor closeClient = NettyClientAcceptor.this;

                LOGGER.warn(
                        "XXX 注意: 服务器连接关闭! {} XXX",
                        _usingConf
                );

                _ready = false;
                closeClient._ch = null;

                // 获取并执行关闭回调函数
                final ICloseCallback callback = _usingConf.getCloseCallback();

                if (null != callback) {
                    callback.apply(closeClient);
                }
            });

            // 只有是WS的形式，才需要升级握手
            if (_usingConf.getServerType() == Config.WS) {
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
                    _usingConf
            );
            // 准备完成
            _ready = true;
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

        if (null == _ch) {
            LOGGER.error("client channel is null");
            return;
        }

        _ch.writeAndFlush(msgObj);
    }

    /**
     * 配置
     */
    static public final class Config {

        /**
         * C/S连接模式
         */
        public static final int APP = 1;

        /**
         * WebSocket连接模式
         */
        public static final int WS = 2;

        /**
         * 服务器类型
         */
        private int _serverType = WS;

        /**
         * 服务器 Id
         */
        private int _serverId;

        /**
         * 服务器名称
         */
        private String _serverName;

        /**
         * 服务器工作类型集合
         */
        private Set<String> _serverJobTypeSet;

        /**
         * 服务器地址
         */
        private String _serverHost;

        /**
         * 服务器端口号
         */

        private int _serverPort;

        /**
         * WebSocket连接地址
         */
        private String _path = "ws";

        /**
         * 是否为安全连接
         */
        private Boolean _safe = false;

        /**
         * 信道处理器工厂
         */
        @Expose(serialize = false, deserialize = false)
        private AbstractChannelHandlerFactory _channelHandlerFactory;

        /**
         * 连接关闭
         */
        @Expose(serialize = false, deserialize = false)
        private ICloseCallback _closeCallback;


        /**
         * 根据safe验证是否为需要签名认证的前缀
         *
         * @return prefix
         */
        public String getWsPrefix() {
            return this._safe ? "wss" : "ws";
        }

        @SerializedName(value = "path")
        public String getPath() {
            return _path;
        }

        public void setPath(String path) {
            this._path = path;
        }


        @SerializedName(value = "safe")
        public Boolean getSafe() {
            return _safe;
        }

        public void setSafe(Boolean safe) {
            this._safe = safe;
        }

        @SerializedName(value = "serverType")
        public int getServerType() {
            return _serverType;
        }

        public void setServerType(int _serverType) {
            this._serverType = _serverType;
        }

        /**
         * 获取服务器 Id
         *
         * @return 服务器 Id
         */
        @SerializedName(value = "serverId")
        public int getServerId() {
            return _serverId;
        }

        /**
         * 设置服务器 Id
         *
         * @param val 整数值
         */
        public void setServerId(int val) {
            _serverId = val;
        }

        /**
         * 获取服务器名称
         *
         * @return 服务器名称
         */
        @SerializedName(value = "serverName")
        public String getServerName() {
            return _serverName;
        }

        /**
         * 设置服务器名称
         *
         * @param val 字符串值
         */
        public void setServerName(String val) {
            _serverName = val;
        }

        /**
         * 获取服务器工作类型集合
         *
         * @return 服务器工作类型集合
         */
        @SerializedName(value = "serverJobTypeSet")
        public Set<String> getServerJobTypeSet() {
            if (CollectionUtils.isEmpty(_serverJobTypeSet)) {
                return Collections.emptySet();
            }
            return _serverJobTypeSet;
        }

        /**
         * 设置服务器工作类型集合
         *
         * @param val 字符串集合
         */
        public void setServerJobTypeSet(Set<String> val) {
            _serverJobTypeSet = val;
        }

        /**
         * 获取服务器地址
         *
         * @return 服务器地址
         */
        @SerializedName(value = "serverHost")
        public String getServerHost() {
            return _serverHost;
        }

        /**
         * 设置服务器地址
         *
         * @param val 字符串值
         */
        public void setServerHost(String val) {
            _serverHost = val;
        }

        /**
         * 获取服务器端口号
         *
         * @return 服务器端口号
         */
        @SerializedName(value = "serverPort")
        public int getServerPort() {
            return _serverPort;
        }

        /**
         * 设置服务器端口号
         *
         * @param val 整数值
         */
        public void setServerPort(int val) {
            _serverPort = val;
        }

        /**
         * 获取信道处理器工厂
         *
         * @return 信道处理器工厂
         */
        public AbstractChannelHandlerFactory getChannelHandlerFactory() {
            return _channelHandlerFactory;
        }

        /**
         * 设置信道处理器工厂
         *
         * @param val 对象值
         */
        public void setChannelHandlerFactory(AbstractChannelHandlerFactory val) {
            _channelHandlerFactory = val;
        }

        /**
         * 获取服务器关闭回调函数
         *
         * @return 服务器关闭回调函数
         */
        public ICloseCallback getCloseCallback() {
            return _closeCallback;
        }

        /**
         * 设置服务器关闭回调函数
         *
         * @param val 对象值
         */
        public void setCloseCallback(ICloseCallback val) {
            _closeCallback = val;
        }

        @Override
        public String toString() {
            return MessageFormat.format(
                    "serverId = {0}, serverName = {1}, serverJobType = {2}, addr = {3}:{4}",
                    String.valueOf(_serverId),
                    _serverName,
                    _serverJobTypeSet,
                    _serverHost,
                    String.valueOf(_serverPort)
            );
        }

        /**
         * 从 JSON 对象中创建配置
         *
         * @param jsonObj JSON 对象
         * @return 配置
         */
        static public Config fromJSONObj(JsonObject jsonObj) {
            if (null == jsonObj) {
                return null;
            }

            return GSON.fromJson(jsonObj, Config.class);
        }

        /**
         * 抽象的信道处理器工厂
         */
        static public abstract class AbstractChannelHandlerFactory {
            /**
             * 创建消息处理器
             *
             * @return 消息处理器
             */
            public abstract ChannelHandler createMsgHandler();
        }
    }

    /**
     * 关闭回调接口
     */
    public interface ICloseCallback {
        /**
         * 执行回调
         *
         * @param closeClient 关闭客户端
         */
        void apply(NettyClientAcceptor closeClient);
    }

    /**
     * 获取服务器 Id
     *
     * @return 服务器 Id
     */
    public int getServerId() {
        return _usingConf.getServerId();
    }

    /**
     * 获取服务器名称
     *
     * @return 服务器名称
     */
    public String getServerName() {
        return _usingConf.getServerName();
    }

    /**
     * 获取服务器工作类型数组
     *
     * @return 服务器工作类型数组
     */
    public Set<String> getServerJobTypeSet() {
        return _usingConf.getServerJobTypeSet();
    }

    /**
     * 获取服务器主机地址
     *
     * @return 服务器主机地址
     */
    public String getServerHost() {
        return _usingConf.getServerHost();
    }

    /**
     * 获取服务器端口号
     *
     * @return 服务器端口号
     */
    public int getServerPort() {
        return _usingConf.getServerPort();
    }

    /**
     * 是否准备好
     *
     * @return true = 已准备好, false = 未准备好
     */
    public boolean isReady() {
        return _ready;
    }
}
