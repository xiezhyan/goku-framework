package top.zopx.starter.distribution.service;

/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
public interface ILockService {

    /**
     * 加锁
     * @param key 加锁唯一标识
     */
    void lock(String key);

    /**
     * 删除锁
     * @param key 加锁唯一标识
     */
    void unLock(String key);
}
