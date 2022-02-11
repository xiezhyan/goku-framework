package top.zopx.starter.netty.configurator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.zopx.square.netty.configurator.properties.NettyServerConfig;
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

    private NettyServerConfig.App app;

    private NettyServerConfig.Ws ws = new NettyServerConfig.Ws();

    public NettyServerConfig.App getApp() {
        return app;
    }

    public void setApp(NettyServerConfig.App app) {
        this.app = app;
    }

    public NettyServerConfig.Ws getWs() {
        return ws;
    }

    public void setWs(NettyServerConfig.Ws ws) {
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
