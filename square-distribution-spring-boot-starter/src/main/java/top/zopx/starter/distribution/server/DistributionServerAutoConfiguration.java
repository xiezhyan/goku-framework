package top.zopx.starter.distribution.server;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.zopx.starter.distribution.aspect.DistributionAspect;
import top.zopx.starter.distribution.configurator.JvmInitialConfigurator;
import top.zopx.starter.distribution.configurator.RedisInitialConfigurator;
import top.zopx.starter.distribution.configurator.ZookeeperInitialConfigurator;
import top.zopx.starter.distribution.properties.SquareDistributionProperties;
import top.zopx.starter.distribution.service.ILockService;
import top.zopx.starter.distribution.service.impl.jvm.ReentrantLockServiceImpl;
import top.zopx.starter.distribution.service.impl.redis.RedisLockServiceImpl;
import top.zopx.starter.distribution.service.impl.zookeeper.ZookeeperLockServiceImpl;

/**
 * @author sanq.Yan
 * @date 2021/3/29
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(DistributionMarkerConfiguration.Marker.class)
@EnableConfigurationProperties({ SquareDistributionProperties.class })
@Import({RedisInitialConfigurator.class, DistributionAspect.class, ZookeeperInitialConfigurator.class, JvmInitialConfigurator.class})
public class DistributionServerAutoConfiguration {

    @Bean
    @ConditionalOnBean(RedisInitialConfigurator.class)
    public ILockService redisLockService() {
        return new RedisLockServiceImpl();
    }

    @Bean
    @ConditionalOnBean(ZookeeperInitialConfigurator.class)
    public ILockService zookeeperLockService() {
        return new ZookeeperLockServiceImpl();
    }


    @Bean
    @ConditionalOnBean(JvmInitialConfigurator.class)
    public ILockService reentrantLockService() {
        return new ReentrantLockServiceImpl();
    }

}
