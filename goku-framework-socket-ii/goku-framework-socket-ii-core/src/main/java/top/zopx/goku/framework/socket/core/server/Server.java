package top.zopx.goku.framework.socket.core.server;

import top.zopx.goku.framework.socket.core.cmd.IChannelHandle;

import java.time.Duration;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/23
 */
public class Server {

    private final Duration readTimeout;

    private final Duration writeTimeout;

    private final Integer bossThreadPool;

    private final Integer workThreadPool;

    private final App app;

    private final Websocket websocket;

    private final IChannelHandle channelHandle;

    public Server(Builder builder) {
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.bossThreadPool = builder.bossThreadPool;
        this.workThreadPool = builder.workThreadPool;
        this.app = builder.app;
        this.websocket = builder.websocket;
        this.channelHandle = builder.channelHandle;
    }

    public static Builder create() {
        return new Builder();
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

    public App getApp() {
        return app;
    }

    public Websocket getWebsocket() {
        return websocket;
    }

    public IChannelHandle getChannelHandle() {
        return channelHandle;
    }

    public static class Builder {

        private Duration readTimeout = Duration.ofSeconds(45L);

        private Duration writeTimeout = Duration.ofSeconds(60L);

        private Integer bossThreadPool = 2;

        private Integer workThreadPool = 8;

        private Websocket websocket = Websocket.create().build();

        private App app;

        private IChannelHandle channelHandle;

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

        public Builder setWebsocket(Websocket websocket) {
            this.websocket = websocket;
            return this;
        }

        public Builder setApp(App app) {
            this.app = app;
            return this;
        }

        public Builder setChannelHandle(IChannelHandle channelHandle) {
            this.channelHandle = channelHandle;
            return this;
        }

        public Server build() {
            return new Server(this);
        }
    }


}
