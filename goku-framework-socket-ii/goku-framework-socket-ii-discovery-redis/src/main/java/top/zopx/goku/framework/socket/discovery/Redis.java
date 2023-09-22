package top.zopx.goku.framework.socket.discovery;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.zopx.goku.framework.socket.discovery.configure.JedisConfigure;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 13:20
 */
public final class Redis {
    private static final Logger LOG = LoggerFactory.getLogger(Redis.class);

    private Redis() {
    }

    private static final Map<String, JedisPool> JEDIS_POOL_MAP =
            new ConcurrentHashMap<>(16);

    private static final Map<String, JedisConfigure> CONFIGURE_MAP =
            new ConcurrentHashMap<>(16);

    public static void init(JsonObject jsonObj) {
        if (null == jsonObj) {
            LOG.error("redis 初始化失败");
            return;
        }

        jsonObj.entrySet()
                .forEach(entry -> {
                    String key = entry.getKey();
                    JsonElement value = entry.getValue();

                    if (StringUtils.isBlank(key) || null == value) {
                        return;
                    }

                    JedisConfigure configure = GsonUtil.getInstance().getGson()
                            .fromJson(value, JedisConfigure.class);

                    CONFIGURE_MAP.put(key, configure);

                    JedisPool jedisPool =
                            new JedisPool(configure.getHost(), configure.getPort());
                    LOG.debug(
                            "JedisPool初始化成功， key={}, host={}, port={}",
                            key,
                            configure.getHost(),
                            configure.getPort()
                    );
                    JEDIS_POOL_MAP.put(key, jedisPool);
                });
    }

    public static Jedis getMain() {
        return get("server");
    }

    public static Jedis getLock() {
        return get("lock");
    }

    private static Jedis get(String key) {
        if (StringUtils.isBlank(key)) {
            LOG.error("key is null");
            throw new BusException("key is null", IBus.ERROR_CODE);
        }

        key = key.toLowerCase();
        JedisPool jedisPool = JEDIS_POOL_MAP.get(key);

        if (null == jedisPool) {
            throw new BusException(MessageFormat.format("{0} 未被初始化", key), IBus.ERROR_CODE);
        }

        Jedis jedis = jedisPool.getResource();
        JedisConfigure configure = CONFIGURE_MAP.get(key);

        if (StringUtils.isNotBlank(configure.getPassword())) {
            jedis.auth(configure.getPassword());
        }

        if (null != configure.getIndex()) {
            jedis.select(configure.getIndex());
        }

        return jedis;
    }

}
