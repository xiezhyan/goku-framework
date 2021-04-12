package top.zopx.starter.distribution.properties.zookeeper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.zopx.starter.distribution.properties.DistributionProperties;

/**
 * @author sanq.Yan
 * @date 2021/3/31
 */
@Component
@Configuration
@ConfigurationProperties(Zookeeper.PREFIX)
public class Zookeeper {
    public static final String PREFIX = DistributionProperties.DISTRIBUTION_PREFIX + ".zookeeper";

    /**
     * 是否开启
     */
    private boolean open = false;

    /**
     * zookeeper连接地址
     */
    private String zookeeperUrl;

    /**
     * 会话超时时间
     */
    private int sessionTimeout = 60000;

    /**
     * 连接超时时间
     */
    private int connectTimeout = 15000;

    /**
     * 最大重试次数
     */
    private int maxRetry = 10;

    /**
     * zookeeper中加锁根路径
     */
    private String root = "/lock/";

    public String getZookeeperUrl() {
        return zookeeperUrl;
    }

    public void setZookeeperUrl(String zookeeperUrl) {
        this.zookeeperUrl = zookeeperUrl;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public String getRoot() {
        if (!this.root.endsWith("/")) {
            return this.root + "/";
        }
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
