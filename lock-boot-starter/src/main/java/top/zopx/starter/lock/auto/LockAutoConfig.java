package top.zopx.starter.lock.auto;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.lock.config.LockProperties;
import top.zopx.starter.lock.service.LockService;
import top.zopx.starter.lock.service.impl.ZookeeperLockService;

/**
 * top.zopx.starter.lock.auto.LockAutoConfig
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Configuration
@EnableConfigurationProperties(LockProperties.class)
public class LockAutoConfig {

    @Bean
    @ConditionalOnClass(CuratorFramework.class)
    public LockService zookeeperLockService() {
        return new ZookeeperLockService();
    }
}
