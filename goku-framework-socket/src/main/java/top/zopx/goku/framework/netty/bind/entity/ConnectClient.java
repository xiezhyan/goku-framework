package top.zopx.goku.framework.netty.bind.entity;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import top.zopx.goku.framework.netty.bind.factory.BaseChannelHandlerFactory;
import top.zopx.goku.framework.netty.server.GatewayToBizServerAcceptor;
import top.zopx.goku.framework.tools.util.json.JsonUtil;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 20:31
 */
public final class ConnectClient {

    public interface Constant {
        /**
         * C/S连接模式
         */
        int APP = 1;

        /**
         * WebSocket连接模式
         */
        int WS = 2;

    }


    /**
     * 服务器类型
     */
    private final int serverType;

    /**
     * 服务器 Id
     */
    private final int serverId;

    /**
     * 服务器名称
     */
    private final String serverName;

    /**
     * 服务器工作类型集合
     */
    private final Set<String> serverJobTypeSet;

    /**
     * 服务器地址
     */
    private final String serverHost;

    /**
     * 服务器端口号
     */
    private final int serverPort;

    /**
     * websocket地址
     */
    private final String websocketPath;

    /**
     * 信道处理器工厂
     * <pre>
     * {@code
     *     userEventTriggered(ChannelHandlerContext ctx, Object eventObj) {
     *         if (null == ctx ||
     *             !(eventObj instanceof ClientHandshakeStateEvent)) {
     *             return;
     *         }
     *         ClientHandshakeStateEvent
     *             realEvent = (ClientHandshakeStateEvent) eventObj;
     *         if (ClientHandshakeStateEvent.HANDSHAKE_COMPLETE != realEvent) {
     *             return;
     *         }
     *         // 执行 Ping 心跳
     *         ScheduledFuture<?> _pingHeartbeat = Timer.scheduleWithFixedDelay(
     *             () -> doPing(ctx),
     *             PING_INTERVAL_TIME, PING_INTERVAL_TIME,
     *             TimeUnit.MILLISECONDS
     *         );
     *     }
     *
     *     channelRead() {
     *      ClientChannelGroup.writeAndFlushBySessionId(
     *          realMsg, // 该消息会经过 ClientMsgEncoder 编码
     *          realMsg.getRemoteSessionId()
     *      );
     *     }
     *
     *     channelInactive() {
     *      _pingHeartbeat.cancel(true);
     *     }
     * }
     * </pre>
     */
    @Expose(serialize = false, deserialize = false)
    private final BaseChannelHandlerFactory channelHandlerFactory;

    /**
     * 连接关闭
     */
    @Expose(serialize = false, deserialize = false)
    private final ICloseCallback closeCallback;

    private ConnectClient(Builder builder) {
        this.serverType = builder.serverType;
        this.serverId = builder.serverId;
        this.serverName = builder.serverName;
        this.serverJobTypeSet = builder.serverJobTypeSet;
        this.serverHost = builder.serverHost;
        this.serverPort = builder.serverPort;
        this.websocketPath = initWebsocketPath(builder);
        this.channelHandlerFactory = builder.channelHandlerFactory;
        this.closeCallback = builder.closeCallback;
    }

    private String initWebsocketPath(Builder builder) {
        String protoc = "ws";
        if (Boolean.TRUE.equals(builder.safe)) {
            protoc = "wss";
        }
        String path = builder.path.startsWith("/") ? builder.path : "/" + builder.path;
        return MessageFormat.format("{0}://{1}:{2}{3}", protoc, builder.serverHost, String.valueOf(builder.serverPort), path);
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        /**
         * 服务器类型
         */
        private int serverType = Constant.WS;

        /**
         * 服务器 Id
         */
        private int serverId;

        /**
         * 服务器名称
         */
        private String serverName;

        /**
         * 服务器工作类型集合
         */
        private final Set<String> serverJobTypeSet = new HashSet<>();

        /**
         * 服务器地址
         */
        private String serverHost;

        /**
         * 服务器端口号
         */

        private int serverPort;

        /**
         * WebSocket连接地址
         */
        private String path = "/ws";

        /**
         * 是否为安全连接
         */
        private Boolean safe = false;

        /**
         * 信道处理器工厂
         */
        @Expose(serialize = false, deserialize = false)
        private BaseChannelHandlerFactory channelHandlerFactory;

        /**
         * 连接关闭
         */
        @Expose(serialize = false, deserialize = false)
        private ICloseCallback closeCallback;

        public Builder setServerType(int serverType) {
            this.serverType = serverType;
            return this;
        }

        public Builder setServerId(int serverId) {
            this.serverId = serverId;
            return this;
        }

        public Builder setServerName(String serverName) {
            this.serverName = serverName;
            return this;
        }

        public Builder setServerJobTypeSet(Set<String> serverJobTypeSet) {
            this.serverJobTypeSet.addAll(serverJobTypeSet);
            return this;
        }

        public Builder setServerHost(String serverHost) {
            this.serverHost = serverHost;
            return this;
        }

        public Builder setServerPort(int serverPort) {
            this.serverPort = serverPort;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setSafe(Boolean safe) {
            this.safe = safe;
            return this;
        }

        public Builder setChannelHandlerFactory(BaseChannelHandlerFactory channelHandlerFactory) {
            this.channelHandlerFactory = channelHandlerFactory;
            return this;
        }

        public Builder setCloseCallback(ICloseCallback closeCallback) {
            this.closeCallback = closeCallback;
            return this;
        }

        public ConnectClient build() {
            return new ConnectClient(this);
        }

        /**
         * 从 JSON 对象中创建配置
         *
         * @param jsonObj JSON 对象
         * @return 配置
         */
        public Builder fromJsonData(JsonObject jsonObj) {
            if (null == jsonObj) {
                return null;
            }

            return JsonUtil.getInstance().getGson().fromJson(jsonObj, Builder.class);
        }
    }

    public int getServerType() {
        return serverType;
    }

    public int getServerId() {
        return serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public Set<String> getServerJobTypeSet() {
        return serverJobTypeSet;
    }

    public String getServerHost() {
        return serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getWebsocketPath() {
        return websocketPath;
    }

    public BaseChannelHandlerFactory getChannelHandlerFactory() {
        return channelHandlerFactory;
    }

    public ICloseCallback getCloseCallback() {
        return closeCallback;
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
        void apply(GatewayToBizServerAcceptor closeClient);
    }

    public static void main(String[] args) {
        ConnectClient client = ConnectClient.create().setServerHost("123").setServerPort(555).setPath("/ws").build();
        System.out.println(client.getWebsocketPath());

    }
}
