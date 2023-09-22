package top.zopx.goku.framework.socket.netty.properties;

import com.google.gson.annotations.Expose;
import top.zopx.goku.framework.socket.netty.handle.IChannelHandle;
import top.zopx.goku.framework.socket.netty.runner.MultiServerRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/23
 */
public class MultiServer {

    private final ServerEnum serverType;

    private final int serverId;

    private final String serverName;

    private final Set<String> serverJobTypeSet;

    private final String serverHost;

    private final int serverPort;

    private final String websocketPath;

    @Expose(serialize = false, deserialize = false)
    private final IChannelHandle channelHandle;

    @Expose(serialize = false, deserialize = false)
    private ICloseCallback closeCallback;

    MultiServer(Builder builder) {
        this.serverType = builder.serverType;
        this.serverId = builder.serverId;
        this.serverName = builder.serverName;
        this.serverJobTypeSet = builder.serverJobTypeSet;
        this.serverHost = builder.serverHost;
        this.serverPort = builder.serverPort;
        this.websocketPath = buildInitialWebsocketPath(builder);
        this.channelHandle = builder.channelHandle;
        this.closeCallback = builder.closeCallback;
    }

    public static Builder create() {
        return new Builder();
    }

    private String buildInitialWebsocketPath(Builder builder) {
        return String.format(
                "%s://%s:%d%s",
                Boolean.TRUE.equals(builder.safe) ? "wss" : "ws",
                builder.serverHost,
                builder.serverPort,
                builder.websocketPath
        );
    }

    public ServerEnum getServerType() {
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

    public IChannelHandle getChannelHandle() {
        return channelHandle;
    }

    public ICloseCallback getCloseCallback() {
        return closeCallback;
    }

    public static class Builder {

        private ServerEnum serverType = ServerEnum.WEB_SOCKET;

        private int serverId;

        private String serverName;

        private final Set<String> serverJobTypeSet = new HashSet<>();

        private String serverHost = "127.0.0.1";

        private int serverPort;

        private String websocketPath = "/ws";

        private Boolean safe = Boolean.FALSE;

        @Expose(serialize = false, deserialize = false)
        private IChannelHandle channelHandle;

        @Expose(serialize = false, deserialize = false)
        private ICloseCallback closeCallback;

        public Builder setServerType(ServerEnum serverType) {
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

        public Builder setWebsocketPath(String websocketPath) {
            this.websocketPath = websocketPath;
            return this;
        }

        public Builder setChannelHandle(IChannelHandle channelHandle) {
            this.channelHandle = channelHandle;
            return this;
        }

        public Builder setCloseCallback(ICloseCallback closeCallback) {
            this.closeCallback = closeCallback;
            return this;
        }

        public Builder setSafe(Boolean safe) {
            this.safe = safe;
            return this;
        }

        public MultiServer build() {
            return new MultiServer(this);
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
        void apply(MultiServerRunner closeClient);
    }

    public enum ServerEnum {
        APP,
        WEB_SOCKET,
        ;
    }
}
