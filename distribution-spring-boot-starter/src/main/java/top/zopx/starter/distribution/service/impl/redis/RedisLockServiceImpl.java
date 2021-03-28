package top.zopx.starter.distribution.service.impl.redis;

import top.zopx.starter.distribution.properties.DistributionProperties;
import top.zopx.starter.distribution.properties.redis.Redis;
import top.zopx.starter.distribution.service.ILockService;

import javax.annotation.Resource;

/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
public class RedisLockServiceImpl implements ILockService {

    @Resource
    private DistributionProperties distributionProperties;

    @Override
    public void lock(String key) {
        if (distributionProperties.getRedis().isOpen()) {
            // 开始加锁
        }
    }

    @Override
    public void unLock(String key) {
        if (distributionProperties.getRedis().isOpen()) {
            // 开始解锁
        }
    }
}
