package top.zopx.goku.framework.socket.core.server;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/23
 */
public class App {

    private final String host;

    private final Integer port;

    App(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
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

    public static class Builder {

        private String host = "127.0.0.1";

        private Integer port = 54321;

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(Integer port) {
            this.port = port;
            return this;
        }

        public App build() {
            return new App(this);
        }
    }
}
