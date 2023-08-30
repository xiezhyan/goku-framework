package top.zopx.goku.framework.socket.redis;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.zopx.goku.framework.socket.core.config.properties.BootstrapRedis;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/25 18:31
 */
public final class Redis {

    private static final Logger LOGGER = LoggerFactory.getLogger(Redis.class);

    private Redis() {
    }

    /**
     * 服务类型和线程池
     */
    private static final Map<String, JedisPool> JEDIS_POOL_MAP = new ConcurrentHashMap<>(1 << 4);

    private static Map<String, BootstrapRedis> map;

    public static void init(Map<String, BootstrapRedis> map) {
        if (MapUtils.isEmpty(map)) {
            LOGGER.error("redis init error");
            return;
        }

        Redis.map = map;
        map.forEach((key, item) -> {
            if (null == key ||
                    null == item) {
                return;
            }

            try {
                JedisPool jedisPool = new JedisPool(
                        item.getHost(), item.getPort()
                );
                LOGGER.debug(
                        "JedisPool初始化成功， key={}, host={}, port={}",
                        key,
                        item.getHost(),
                        item.getPort()
                );

                JEDIS_POOL_MAP.put(key, jedisPool);
            } catch (Exception e) {
                LOGGER.error("初始化异常", e);
            }
        });
    }

    /**
     * 业务处理
     *
     * @return Jedis
     */
    public static Jedis get() {
        return get("server");
    }

    public static Jedis get(String key) {
        if (StringUtils.isBlank(key)) {
            LOGGER.error("key is null");
            throw new BusException("key is null", IBus.ERROR_CODE);
        }

        key = key.toLowerCase();
        JedisPool jedisPool = JEDIS_POOL_MAP.get(key);

        if (null == jedisPool) {
            throw new BusException(MessageFormat.format("{0} 未被初始化", key), IBus.ERROR_CODE);
        }

        BootstrapRedis bootstrapRedis = Redis.map.get(key);
        Jedis jedis = jedisPool.getResource();

        if (StringUtils.isNotBlank(bootstrapRedis.getPassword())) {
            jedis.auth(bootstrapRedis.getPassword());
        }

        if (Objects.nonNull(bootstrapRedis.getIndex())) {
            jedis.select(bootstrapRedis.getIndex());
        }
        return jedis;
    }
}
