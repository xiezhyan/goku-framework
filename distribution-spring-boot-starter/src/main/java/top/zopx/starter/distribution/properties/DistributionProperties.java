package top.zopx.starter.distribution.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import top.zopx.starter.distribution.properties.jvm.Jvm;
import top.zopx.starter.distribution.properties.redis.Redis;
import top.zopx.starter.distribution.properties.zookeeper.Zookeeper;

/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
@ConfigurationProperties(DistributionProperties.DISTRIBUTION_PREFIX)
public class DistributionProperties {

    public static final String DISTRIBUTION_PREFIX = "distribution";

    /**
     * 重试次数
     */
    private int retryAttempts = 3;

    private Redis redis;

    private Zookeeper zookeeper;

    private Jvm jvm;

    public Jvm getJvm() {
        return jvm;
    }

    public void setJvm(Jvm jvm) {
        this.jvm = jvm;
    }

    public Redis getRedis() {
        return redis;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    public int getRetryAttempts() {
        return retryAttempts;
    }

    public void setRetryAttempts(int retryAttempts) {
        this.retryAttempts = retryAttempts;
    }

    public Zookeeper getZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(Zookeeper zookeeper) {
        this.zookeeper = zookeeper;
    }
}
