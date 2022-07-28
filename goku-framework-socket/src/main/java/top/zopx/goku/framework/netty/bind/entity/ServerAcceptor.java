package top.zopx.goku.framework.netty.bind.entity;

import top.zopx.goku.framework.netty.bind.factory.BaseChannelHandlerFactory;

import java.time.Duration;

/**
 * @author 俗世游子
 * @date 2021/10/3
 * @email xiezhyan@126.com
 */
public class ServerAcceptor {

    /**
     * 读操作检测时间
     */
    private final Duration readTimeout;

    /**
     * 写操作检测时间
     */
    private final Duration writeTimeout;

    /**
     * 主线程数
     */
    private final Integer bossThreadPool;

    /**
     * 工作线程数
     */
    private final Integer workThreadPool;

    /**
     * App连接
     */
    private final AppClient app;

    /**
     * Websocket客户端连接
     */
    private final WebsocketClient ws;

    /**
     * 处理器
     */
    private final BaseChannelHandlerFactory factory;

    public ServerAcceptor(Builder builder) {
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.bossThreadPool = builder.bossThreadPool;
        this.workThreadPool = builder.workThreadPool;
        this.app = builder.app;
        this.ws = builder.ws;
        this.factory = builder.factory;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {

        /**
         * 读操作检测时间
         */
        private Duration readTimeout = Duration.ofSeconds(45L);

        /**
         * 写操作检测时间
         */
        private Duration writeTimeout = Duration.ofSeconds(60L);

        /**
         * 主线程数
         */
        private Integer bossThreadPool = 2;

        /**
         * 工作线程数
         */
        private Integer workThreadPool = 4;

        /**
         * App连接
         */
        private AppClient app;

        /**
         * Websocket客户端连接
         */
        private WebsocketClient ws = WebsocketClient.create().build();

        /**
         * 处理器
         */
        private BaseChannelHandlerFactory factory;

        public Builder setReadTimeout(Duration readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(Duration writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setBossThreadPool(Integer bossThreadPool) {
            this.bossThreadPool = bossThreadPool;
            return this;
        }

        public Builder setWorkThreadPool(Integer workThreadPool) {
            this.workThreadPool = workThreadPool;
            return this;
        }

        public Builder setApp(AppClient app) {
            this.app = app;
            return this;
        }

        public Builder setWs(WebsocketClient ws) {
            this.ws = ws;
            return this;
        }

        public Builder setFactory(BaseChannelHandlerFactory factory) {
            this.factory = factory;
            return this;
        }

        public ServerAcceptor build() {
            return new ServerAcceptor(this);
        }
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public Duration getWriteTimeout() {
        return writeTimeout;
    }

    public Integer getBossThreadPool() {
        return bossThreadPool;
    }

    public Integer getWorkThreadPool() {
        return workThreadPool;
    }

    public AppClient getApp() {
        return app;
    }

    public WebsocketClient getWs() {
        return ws;
    }

    public BaseChannelHandlerFactory getFactory() {
        return factory;
    }
}
