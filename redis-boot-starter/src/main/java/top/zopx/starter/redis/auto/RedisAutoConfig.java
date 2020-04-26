package top.zopx.starter.redis.auto;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import top.zopx.starter.redis.config.RedisProperties;
import top.zopx.starter.redis.service.RedisPoolService;
import top.zopx.starter.redis.service.impl.RedisPoolServiceImpl;

/**
 * top.zopx.starter.redis.auto.RedisAutoConfig
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfig {

    @Bean
    @ConditionalOnClass(RedisTemplate.class)
    public RedisPoolService redisPoolService() {
        return new RedisPoolServiceImpl();
    }

}
