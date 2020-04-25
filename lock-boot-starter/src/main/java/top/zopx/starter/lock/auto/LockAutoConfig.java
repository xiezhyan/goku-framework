package top.zopx.starter.lock.auto;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import top.zopx.starter.lock.config.LockProperties;
import top.zopx.starter.lock.service.LockService;
import top.zopx.starter.lock.service.impl.ZookeeperLockService;

/**
 * top.zopx.starter.lock.auto.LockAutoConfig
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Order(1)
@Configuration
@EnableConfigurationProperties(LockProperties.class)
@ConditionalOnMissingBean(LockService.class)
public class LockAutoConfig {

    @Bean
    @ConditionalOnClass(CuratorFramework.class)
    public LockService fileManageService() {
        return new ZookeeperLockService();
    }
}
