package top.zopx.goku.framework.support.primary.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.zopx.goku.framework.support.primary.core.entity.BootstrapSnowflakeItem;
import top.zopx.goku.framework.support.primary.configurator.marker.ZookeeperMarkerConfiguration;

import static top.zopx.goku.framework.support.primary.properties.BootstrapSnowflake.PREFIX;

/**
 * 基于Zookeeper相关配置
 *
 * @author Mr.Xie
 * @date 2021/12/25
 * @email xiezhyan@126.com
 */
@Configuration
@ConfigurationProperties(value = PREFIX)
@ConditionalOnBean({ZookeeperMarkerConfiguration.ZookeeperPrimaryMarker.class})
public class BootstrapSnowflake {
    public static final String PREFIX = "goku.primary.snowflake";
    /**
     * 服务名
     */
    private String serverName;

    /**
     * Zookeeper节点信息
     */
    private String zookeeperHost;

    /**
     * 重试次数
     */
    private Integer retry = 5;

    /**
     * 间隔时间
     */
    private Integer sleepMsBetweenRetries = 5000;

    /**
     * snowflake 配置
     */
    private BootstrapSnowflakeItem snowflake;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getZookeeperHost() {
        return zookeeperHost;
    }

    public void setZookeeperHost(String zookeeperHost) {
        this.zookeeperHost = zookeeperHost;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public Integer getSleepMsBetweenRetries() {
        return sleepMsBetweenRetries;
    }

    public void setSleepMsBetweenRetries(Integer sleepMsBetweenRetries) {
        this.sleepMsBetweenRetries = sleepMsBetweenRetries;
    }

    public BootstrapSnowflakeItem getSnowflake() {
        return snowflake;
    }

    public void setSnowflake(BootstrapSnowflakeItem snowflake) {
        this.snowflake = snowflake;
    }
}
