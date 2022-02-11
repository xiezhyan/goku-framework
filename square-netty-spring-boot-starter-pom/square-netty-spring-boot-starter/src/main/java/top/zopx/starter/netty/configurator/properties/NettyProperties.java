package top.zopx.starter.netty.configurator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.tools.constants.PropertiesCons;

import java.time.Duration;

/**
 * @author 俗世游子
 * @date 2022/2/3
 * @email xiezhyan@126.com
 */
@Configuration
@ConfigurationProperties(value = PropertiesCons.Netty.NETTY_PROPERTIES)
public class NettyProperties {
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

    private App app;

    private Ws ws = new Ws();

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

    public static class Ws {
        /**
         * bing host
         */
        private String host = "127.0.0.1";
        /**
         * websocket端绑定端口
         */
        private Integer port = 23457;

        /**
         * 是否为安全设置
         */
        private Boolean safe;

        /**
         * ws 地址
         */
        private String path;

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public Boolean getSafe() {
            return safe;
        }

        public void setSafe(Boolean safe) {
            this.safe = safe;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
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

    public Ws getWs() {
        return ws;
    }

    public void setWs(Ws ws) {
        this.ws = ws;
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
