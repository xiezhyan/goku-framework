package top.zopx.starter.lock.config;

import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * top.zopx.starter.lock.config.LockProperties
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@ConfigurationProperties(prefix = LockProperties.PREFIX)
@Configuration
@Component
@Data
public class LockProperties {
    public static final String PREFIX = "lock";

    private ZookeeperProperties zook;

    @Data
    @Component
    @ConfigurationProperties(prefix = LockProperties.PREFIX + "." + ZookeeperProperties.PREFIX)
    public static class ZookeeperProperties {
        public static final String PREFIX = "zook";

        private String servers;

        private int retryCount = 3;

        private int elapsedTimeMs = 5000;

        private int sessionTimeoutMs = 60000;

        private int connectionTimeoutMs = 5000;

        @Bean
        @ConditionalOnProperty(
                prefix = LockProperties.PREFIX + "." + ZookeeperProperties.PREFIX,
                name = {
                        "servers"
                })
        public CuratorFramework curatorFramework() {
            return CuratorFrameworkFactory.newClient(
                    servers,
                    sessionTimeoutMs,
                    connectionTimeoutMs,
                    new RetryNTimes(retryCount, elapsedTimeMs)
            );
        }
    }
}
