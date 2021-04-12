package top.zopx.starter.distribution.service.impl.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.starter.distribution.properties.DistributionProperties;
import top.zopx.starter.distribution.service.ILockService;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
public class ZookeeperLockServiceImpl implements ILockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperLockServiceImpl.class);

    @Resource
    private DistributionProperties distributionProperties;

    @Resource
    private CuratorFramework curatorFramework;

    @Override
    public void lock(String key) throws Exception {
        if (distributionProperties.getZookeeper().isOpen()) {
            // 开始加锁
            LOGGER.info("starting lock, {}， {}=============", key, Thread.currentThread().getName());
        }
    }

    @Override
    public void unLock(String key) throws Exception {
        if (distributionProperties.getZookeeper().isOpen()) {
            // 开始解锁
            LOGGER.info("starting unlock, {}， {}=============", key, Thread.currentThread().getName());
        }
    }
}
