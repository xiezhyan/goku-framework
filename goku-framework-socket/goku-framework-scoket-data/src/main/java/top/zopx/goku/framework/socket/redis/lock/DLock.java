package top.zopx.goku.framework.socket.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import top.zopx.goku.framework.socket.redis.Redis;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/25 18:47
 */
public class DLock implements AutoCloseable{
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DLock.class);

    /**
     * 分布式锁字典
     */
    private static final Map<String, DLock> D_LOCK_MAP = new ConcurrentHashMap<>();

    /**
     * Redis 关键字前缀
     */
    private static final String REDIS_KEY_PREFIX = "goku:socket:";

    /**
     * 锁名称
     */
    private final String lockName;

    /**
     * 创建时间
     */
    private final long createTime;

    /**
     * 持续时间
     */
    private long duration;

    /**
     * 私有化类参数构造器
     *
     * @param name 锁名称
     */
    private DLock(String name) {
        lockName = name;
        createTime = System.currentTimeMillis();
    }

    /**
     * 获取分布式锁
     *
     * @param name 锁名称
     * @return 分布式锁
     */
    public static  DLock newLock(String name) {
        if (null == name ||
                name.isEmpty()) {
            throw new BusException("name is null or empty", IBus.ERROR_CODE);
        }

        // 事先执行自动清理
        autoClean();

        if (!D_LOCK_MAP.containsKey(name)) {
            D_LOCK_MAP.putIfAbsent(name, new DLock(name));
        }

        return D_LOCK_MAP.get(name);
    }

    /**
     * 尝试加锁
     *
     * @param duration 持续时间 ( 毫秒数 )
     * @return true = 加锁成功, false = 加锁失败
     */
    public boolean tryLock(long duration) {
        if (duration < 0) {
            duration = 1;
        }

        try (Jedis redisCache = Redis.get("lock")) {
            if (null == redisCache) {
                LOGGER.error("redisCache is null");
                return false;
            }

            // 通过 Redis 加锁
            String ok = redisCache.set(
                    getRedisKey(),
                    "yes",
                    new SetParams().nx().px(duration)
            );

            if ("ok".equalsIgnoreCase(ok)) {
                this.duration = duration;
                return true;
            }
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }

        return false;
    }

    /**
     * 释放锁
     */
    public void release() {
        // 从字典中移除
        D_LOCK_MAP.remove(lockName);

        try (Jedis redisCache = Redis.get("lock")) {
            if (null == redisCache) {
                LOGGER.error("redisCache is null");
                return;
            }

            redisCache.del(getRedisKey());
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void close() {
        release();
    }

    /**
     * 自动清理
     */
    private static void autoClean() {
        // 获取当前时间
        final long nowTime = System.currentTimeMillis();
        // 清理掉已经超时的
        D_LOCK_MAP.values().removeIf(dLock ->
                null == dLock || nowTime - dLock.createTime > dLock.duration
        );
    }

    /**
     * 关键 Redis 关键字
     *
     * @return Redis 关键字
     */
    private String getRedisKey() {
        return REDIS_KEY_PREFIX + lockName;
    }

    public static void main(String[] args) {
        try (DLock newLocker = DLock.newLock("check_in_ticket?user_id=1")) {
            if (null == newLocker ||
                    !newLocker.tryLock(5000)) {
                // 增加一个分布式锁, 5 秒种内不能执行相同操作,
                return;
            }
        }
    }
}
