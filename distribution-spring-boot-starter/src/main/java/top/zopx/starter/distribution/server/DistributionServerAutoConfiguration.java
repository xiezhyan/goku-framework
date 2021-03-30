package top.zopx.starter.distribution.server;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.zopx.starter.distribution.aspect.DistributionAspect;
import top.zopx.starter.distribution.configurator.RedisInitialConfigurator;
import top.zopx.starter.distribution.properties.DistributionProperties;
import top.zopx.starter.distribution.service.ILockService;
import top.zopx.starter.distribution.service.impl.redis.RedisLockServiceImpl;

/**
 * @author sanq.Yan
 * @date 2021/3/29
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(DistributionMarkerConfiguration.Marker.class)
@EnableConfigurationProperties({ DistributionProperties.class })
@Import({RedisInitialConfigurator.class, DistributionAspect.class})
public class DistributionServerAutoConfiguration {

    @Bean
    @ConditionalOnBean(RedisInitialConfigurator.class)
    public ILockService redisLockService() {
        return new RedisLockServiceImpl();
    }
}
