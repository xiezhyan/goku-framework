package top.zopx.goku.framework.redis.lock;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.redis.lock.aspect.LockAspect;
import top.zopx.goku.framework.redis.lock.properties.BootstrapLock;
import top.zopx.goku.framework.redis.lock.service.ILock;
import top.zopx.goku.framework.redis.lock.service.impl.RedissonLock;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/23 7:38
 */
@Component
@Configuration(proxyBeanMethods = false)
@Import(LockAspect.class)
@EnableConfigurationProperties({RedisProperties.class, BootstrapLock.class})
public class GokuFrameworkLockAutoConfiguration {

    @Bean
    public RedissonClient redissonClient(
            @Value("${spring.data.redis.host}") String host,
            @Value("${spring.data.redis.port}") int port,
            @Value("${spring.data.redis.database:15}") int database,
            @Value("${spring.data.redis.password:}") String password
    ) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port).setDatabase(database);
        if (StringUtils.isNotBlank(password)) {
            config.useSingleServer().setPassword(password);
        }
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnMissingBean(ILock.class)
    public ILock lock(BootstrapLock bootstrapLock, RedissonClient redissonClient) {
        return new RedissonLock(bootstrapLock.getLockType(), redissonClient);
    }
}
