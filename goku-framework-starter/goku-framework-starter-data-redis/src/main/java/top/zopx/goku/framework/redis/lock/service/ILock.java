package top.zopx.goku.framework.redis.lock.service;


import java.util.concurrent.TimeUnit;

/**
 * 锁配置
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/23 7:38
 */
public interface ILock {
    /**
     * 加锁
     *
     * @param key         key
     * @param expireTime  过期时间
     * @param maxWaitTime 最大等待时间
     * @param unit        单位
     * @return 是否加锁成功
     */
    boolean lock(String key, int expireTime, int maxWaitTime, TimeUnit unit) throws InterruptedException;

    /**
     * 解锁
     */
    void unLock();
}
