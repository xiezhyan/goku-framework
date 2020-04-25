package top.zopx.starter.lock.service;

/**
 * top.zopx.starter.lock.service.LockService
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
public interface LockService {

    void lock(String key);

    void unLock();
}
