package top.zopx.goku.framework.socket.parse;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 20:07
 */
public class AppClient {
    /**
     * bing host
     */
    private final String host;
    /**
     * APP端绑定端口
     */
    private final Integer port;

    public AppClient(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        /**
         * bing host
         */
        private String host = "127.0.0.1";
        /**
         * APP端绑定端口
         */
        private Integer port = 23456;

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(Integer port) {
            this.port = port;
            return this;
        }

        public AppClient build() {
            return new AppClient(this);
        }
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }
}
