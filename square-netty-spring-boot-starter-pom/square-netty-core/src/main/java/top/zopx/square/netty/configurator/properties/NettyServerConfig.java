package top.zopx.square.netty.configurator.properties;

import java.time.Duration;

/**
 * @author 俗世游子
 * @date 2021/10/3
 * @email xiezhyan@126.com
 */
public class NettyServerConfig {

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

    private App app = new App();

    private Webs webs = new Webs();

    public static class App {
        /**
         * bing host
         */
        private String host = "127.0.0.1";
        /**
         * APP端绑定端口
         */
        private Integer port = 23456;

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }

    public static class Webs {
        /**
         * bing host
         */
        private String host = "127.0.0.1";
        /**
         * websocket端绑定端口
         */
        private Integer port = 23457;

        private String wsPath;

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getWsPath() {
            return wsPath;
        }

        public void setWsPath(String wsPath) {
            this.wsPath = wsPath;
        }
        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Webs getWebs() {
        return webs;
    }

    public void setWebs(Webs webs) {
        this.webs = webs;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Duration getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Integer getBossThreadPool() {
        return bossThreadPool;
    }

    public void setBossThreadPool(Integer bossThreadPool) {
        this.bossThreadPool = bossThreadPool;
    }

    public Integer getWorkThreadPool() {
        return workThreadPool;
    }

    public void setWorkThreadPool(Integer workThreadPool) {
        this.workThreadPool = workThreadPool;
    }
}
