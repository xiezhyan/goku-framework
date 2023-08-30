package top.zopx.goku.framework.redis.lock.service.impl;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import top.zopx.goku.framework.redis.lock.properties.BootstrapLock;
import top.zopx.goku.framework.redis.lock.service.ILock;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.util.concurrent.TimeUnit;

/**
 * 锁配置
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/23 7:38
 */
public class RedissonLock implements ILock {

    /**
     * 加锁类型
     */
    private final BootstrapLock.LockEnum type;

    /**
     * 加锁客户端
     */
    private final RedissonClient redissonClient;

    private static final ThreadLocal<RLock> THREAD_LOCAL = new ThreadLocal<>();

    public RedissonLock(BootstrapLock.LockEnum type, RedissonClient redissonClient) {
        this.type = type;
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean lock(String key, int expireTime, int maxWaitTime, TimeUnit unit) throws InterruptedException {
        RLock rLock = getLock(key);
        THREAD_LOCAL.set(rLock);
        return rLock.tryLock(maxWaitTime, expireTime, unit);
    }

    @Override
    public void unLock() {
        THREAD_LOCAL.get().unlock();
        THREAD_LOCAL.remove();
    }

    private RLock getLock(String lockKey) {
        return switch (type) {
            case FAIR_LOCK -> redissonClient.getFairLock(lockKey);
            case SPIN_LOCK -> redissonClient.getSpinLock(lockKey);
            case REENTRANT_LOCK -> redissonClient.getLock(lockKey);
            default -> throw new BusException("unknown lock type...", IBus.ERROR_CODE);
        };
    }
}
