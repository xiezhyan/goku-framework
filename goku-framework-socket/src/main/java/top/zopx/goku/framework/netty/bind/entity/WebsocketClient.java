package top.zopx.goku.framework.netty.bind.entity;

import java.text.MessageFormat;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 20:09
 */
public class WebsocketClient {

    /**
     * bing host
     */
    private final String host;
    /**
     * websocket端绑定端口
     */
    private final Integer port;

    /**
     * 是否为安全设置
     */
    private final Boolean safe;

    /**
     * ws 地址
     */
    private final String path;

    /**
     * 完成地址: ws://host:port/path
     */
    private final String showPath;

    public WebsocketClient(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.path = builder.path;
        this.safe = builder.safe;
        this.showPath = initWebsocketPath(builder);
    }

    private String initWebsocketPath(Builder builder) {
        String protoc = "ws";
        if (builder.safe) {
            protoc = "wss";
        }
        String path = builder.path.startsWith("/") ? builder.path : "/" + builder.path;
        return MessageFormat.format("{0}://{1}:{2}{3}", protoc, builder.host, String.valueOf(builder.port), path);
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
         * websocket端绑定端口
         */
        private Integer port = 12345;

        /**
         * 是否为安全设置
         */
        private Boolean safe = false;

        /**
         * ws 地址
         */
        private String path = "/ws";

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(Integer port) {
            this.port = port;
            return this;
        }

        public Builder setSafe(Boolean safe) {
            this.safe = safe;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public WebsocketClient build() {
            return new WebsocketClient(this);
        }
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public Boolean getSafe() {
        return safe;
    }

    public String getPath() {
        return path;
    }

    public String getShowPath() {
        return showPath;
    }
}
