package top.zopx.goku.framework.socket.parse;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import top.zopx.goku.framework.socket.handle.BaseChannelHandlerFactory;
import top.zopx.goku.framework.tools.util.json.JsonUtil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 20:31
 */
public final class ConnectClient {

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
     * WebSocket连接地址
     */
    private final String path;

    /**
     * 是否为安全连接
     */
    private final Boolean safe;

    /**
     * 信道处理器工厂
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
        this.path = builder.path;
        this.safe = builder.safe;
        this.channelHandlerFactory = builder.channelHandlerFactory;
        this.closeCallback = builder.closeCallback;
    }

    public static Builder create() {
        return new Builder();
    }

    public String getWsPrefix() {
        return Optional.ofNullable(safe).orElse(false) ? "wss" : "ws";
    }

    public static class Builder {
        /**
         * 服务器类型
         */
        private int serverType = WS;

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
        private String path;

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

    public String getPath() {
        return path;
    }

    public Boolean getSafe() {
        return safe;
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
        void apply(NettyClientAcceptor closeClient);
    }
}
