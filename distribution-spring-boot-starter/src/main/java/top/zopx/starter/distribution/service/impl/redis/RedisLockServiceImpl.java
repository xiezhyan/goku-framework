package top.zopx.starter.distribution.service.impl.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.starter.distribution.properties.DistributionProperties;
import top.zopx.starter.distribution.properties.redis.Redis;
import top.zopx.starter.distribution.service.ILockService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
public class RedisLockServiceImpl implements ILockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockServiceImpl.class);

    @Resource
    private DistributionProperties distributionProperties;
    @Resource
    private RedissonClient redissonClient;
    private RLock rLock;

    @Override
    public void lock(String key) {
        if (distributionProperties.getRedis().isOpen()) {
            // 开始加锁
            LOGGER.info("=============starting lock, {}=============", key);
            rLock = redissonClient.getLock(key);
            rLock.lock();
        }
    }

    @Override
    public void unLock(String key) {
        if (distributionProperties.getRedis().isOpen()) {
            // 开始解锁
            LOGGER.info("=============starting unlock, {}=============", key);
            rLock = redissonClient.getLock(key);
            rLock.unlock();
        }
    }
}
