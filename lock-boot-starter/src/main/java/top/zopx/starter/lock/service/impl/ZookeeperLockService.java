package top.zopx.starter.lock.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.InitializingBean;
import top.zopx.starter.lock.service.LockService;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * top.zopx.starter.lock.service.impl.ZookeeperLockService
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Slf4j
public class ZookeeperLockService implements LockService {

    private static final String ROOT_PATH = "zkLock";

    @Resource
    private CuratorFramework curatorFramework;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private String key;

    private String getLockKey(String key) {
        this.key = key;
        return "/" + ROOT_PATH + "/" + key;
    }

    @Override
    public void lock(String key) {
        log.info("lock:{}", getLockKey(key));
    }

    @Override
    public void unLock() {
        log.info("unLock:{}", getLockKey(this.key));
    }
}
