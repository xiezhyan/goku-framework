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
public class ZookeeperLockService implements LockService, InitializingBean {

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
    public void afterPropertiesSet() throws Exception {
        curatorFramework = curatorFramework.usingNamespace("lock-namespace");

        try {
            String keyPath = "/" + ROOT_PATH;
            if (curatorFramework.checkExists().forPath(keyPath) == null) {
                curatorFramework.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(keyPath);
            }
            addWatcher(ROOT_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addWatcher(String path) throws Exception {
        String keyPath;

        if (path.equals(ROOT_PATH)) {
            keyPath = "/" + path;
        } else {
            keyPath = getLockKey(path);
        }

        final PathChildrenCache cache = new PathChildrenCache(curatorFramework, keyPath, false);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener((client, event) -> {
            if (cache.getListenable().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                String oldPath = event.getData().getPath();
                log.debug("上一个节点{}已经断开", oldPath);
                if (oldPath.contains(path)) {
                    countDownLatch.countDown();
                }
            }
        });
    }

    @Override
    public void lock(String key) {
        String path = getLockKey(key);
        while (true) {
            try {
                if (curatorFramework.checkExists().forPath(path) == null) {
                    curatorFramework
                            .create()
                            .creatingParentsIfNeeded()
                            .withMode(CreateMode.EPHEMERAL)
                            .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                            .forPath(path);
                    log.debug("当前key加锁成功:{}", key);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    if (countDownLatch.getCount() <= 0) {
                        countDownLatch = new CountDownLatch(1);
                    }
                    countDownLatch.await();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void unLock() {
        try {
            String path = getLockKey(this.key);
            if (curatorFramework.checkExists().forPath(path) != null) {
                curatorFramework.delete().forPath(path);
                this.key = "";
                log.debug("当前key解锁成功:{}", this.key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
