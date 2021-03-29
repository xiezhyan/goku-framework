package top.zopx.starter.distribution.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import top.zopx.starter.distribution.properties.redis.Redis;

/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
@ConfigurationProperties(DistributionProperties.DISTRIBUTION_PREFIX)
public class DistributionProperties {

    public static final String DISTRIBUTION_PREFIX = "distribution";

    private int retryAttempts = 3;

    private Redis redis;

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
}
