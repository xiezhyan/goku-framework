package top.zopx.goku.framework.socket.netty.properties;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/23
 */
public class Websocket {

    private final String host;

    private final Integer port;

    private final String path;

    private final Boolean safe;

    Websocket(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.path = builder.path;
        this.safe = builder.safe;
    }

    public static Builder create() {
        return new Builder();
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public Boolean getSafe() {
        return safe;
    }

    public String getInitialWebsocketPath() {
        return String.format("%s://%s:%d%s",Boolean.TRUE.equals(safe) ? "wss": "ws" , host, port, path);
    }

    public static class Builder {
        private String host = "127.0.0.1";
        private Integer port = 12345;
        private String path = "/ws";
        private Boolean safe = Boolean.FALSE;

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(Integer port) {
            this.port = port;
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

        public Websocket build() {
            return new Websocket(this);
        }
    }
}
