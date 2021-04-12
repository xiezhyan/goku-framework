package top.zopx.starter.distribution.service.impl.jvm;

import top.zopx.starter.distribution.properties.DistributionProperties;
import top.zopx.starter.distribution.service.ILockService;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sanq.Yan
 * @date 2021/4/3
 */
public class ReentrantLockServiceImpl implements ILockService {

    @Resource
    private DistributionProperties distributionProperties;

    @Resource
    private ReentrantLock reentrantLock;

    @Override
    public void lock(String key) throws Exception {
        if (distributionProperties.getJvm().isOpen()) {
            reentrantLock.lock();
        }
    }

    @Override
    public void unLock(String key) throws Exception {
        if (distributionProperties.getJvm().isOpen()) {
            reentrantLock.unlock();
        }
    }
}
