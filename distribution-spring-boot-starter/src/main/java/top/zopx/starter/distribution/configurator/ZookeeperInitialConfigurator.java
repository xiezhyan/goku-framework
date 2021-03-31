package top.zopx.starter.distribution.configurator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import top.zopx.starter.distribution.properties.DistributionProperties;
import top.zopx.starter.distribution.properties.zookeeper.Zookeeper;

import javax.annotation.Resource;

/**
 * @author sanq.Yan
 * @date 2021/3/31
 */
@ConditionalOnProperty(prefix = Zookeeper.PREFIX, name = "open", havingValue = "true")
public class ZookeeperInitialConfigurator {

    @Resource
    private DistributionProperties distributionProperties;

    @Bean
    public CuratorFramework curatorFramework() {
        CuratorFramework framework = CuratorFrameworkFactory.builder()
                .connectString(distributionProperties.getZookeeper().getZookeeperUrl())
                .sessionTimeoutMs(distributionProperties.getZookeeper().getSessionTimeout())
                .connectionTimeoutMs(distributionProperties.getZookeeper().getConnectTimeout())
                .retryPolicy(retryPolicy())
                .build();
        framework.start();
        return framework;
    }

    /**
     * 重试机制
     *
     * @return 重试策略
     */
    public RetryPolicy retryPolicy() {
        return new ExponentialBackoffRetry(
                1000,
                distributionProperties.getZookeeper().getMaxRetry(),
                distributionProperties.getRetryAttempts()
        );
    }
}
