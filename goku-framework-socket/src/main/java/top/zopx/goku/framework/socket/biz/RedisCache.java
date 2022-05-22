package top.zopx.goku.framework.socket.biz;

import com.google.gson.JsonObject;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.util.json.JsonUtil;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/4/28
 */
public final class RedisCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCache.class);

    private static Config config;

    /**
     * 服务类型和线程池
     */
    private static final Map<String, JedisPool> JEDIS_POOL_MAP = new ConcurrentHashMap<>();

    /**
     * 初始化连接池
     *
     * @param config 连接配置
     */
    public static void init(Config config) {
        if (Objects.isNull(config) || MapUtils.isEmpty(config.getMap())) {
            throw new IllegalArgumentException("config or config.map is null");
        }

        RedisCache.config = config;
        RedisCache.config.getMap().forEach((key, item) -> {
            if (null == key ||
                    null == item) {
                return;
            }

            try {
                JedisPool jedisPool = new JedisPool(
                        item.getServerHost(), item.getServerPort()
                );
                LOGGER.debug("JedisPool初始化成功， host={}, port={}", item.getServerHost(), item.getServerPort());
                JEDIS_POOL_MAP.put(key, jedisPool);
            } catch (Exception e) {
                LOGGER.error("初始化异常", e);
            }
        });
    }

    /**
     * 服务发现
     *
     * @return Jedis
     */
    public static Jedis getServerCache() {
        return get("server");
    }

    /**
     * 发布订阅
     *
     * @return Jedis
     */
    public static Jedis getPubsub() {
        return get("pubsub");
    }

    /**
     * 获取指定的连接池
     *
     * @param cons 类型
     * @return Jedis
     */
    public static Jedis get(String cons) {
        if (Objects.isNull(cons)) {
            throw new IllegalArgumentException("RedisToolsCons is null");
        }

        JedisPool jedisPool = JEDIS_POOL_MAP.get(cons);
        if (Objects.isNull(jedisPool)) {
            throw new BusException(MessageFormat.format("{0} 未被初始化", cons));
        }

        Config.RedisCacheItemConfig item = config.getMap().get(cons);

        Jedis jedis = jedisPool.getResource();
        if (StringUtils.isNotBlank(item.getPassword())) {
            jedis.auth(item.getPassword());
        }
        if (!Objects.isNull(item.getIndex()) && item.getIndex() > -1) {
            jedis.select(item.getIndex());
        }
        return jedis;
    }

    public static class Config {

        private static final Map<String, RedisCacheItemConfig> MAP = new ConcurrentHashMap<>();

        public Map<String, RedisCacheItemConfig> getMap() {
            return MAP;
        }

        /**
         * 从配置文件中加载配置
         */
        public static Config fromJsonData(JsonObject jsonObj) {
            if (Objects.isNull(jsonObj) || !jsonObj.has("redis")) {
                LOGGER.error("没有对应的配置项，无法加载");
                return null;
            }
            Config config = new Config();

            JsonObject redisObj = jsonObj.getAsJsonObject("redis");
            redisObj.entrySet().forEach(entry ->
                    MAP.put(
                            entry.getKey(),
                            JsonUtil.getInstance().getGson().fromJson(entry.getValue(), RedisCacheItemConfig.class)
                    )
            );
            return config;
        }

        /**
         * 配置类
         */
        public static class RedisCacheItemConfig {
            /**
             * 连接地址
             */
            private String serverHost = "127.0.0.1";

            /**
             * 端口
             */
            private Integer serverPort = 6379;

            /**
             * 连接密码
             */
            private String password;

            /**
             * 索引
             */
            private Integer index = 0;

            public RedisCacheItemConfig() {
            }

            public RedisCacheItemConfig(String serverHost, Integer serverPort, String password, Integer index) {
                this.serverHost = serverHost;
                this.serverPort = serverPort;
                this.password = password;
                this.index = index;
            }

            public String getServerHost() {
                return serverHost;
            }

            public void setServerHost(String serverHost) {
                this.serverHost = serverHost;
            }

            public Integer getServerPort() {
                return serverPort;
            }

            public void setServerPort(Integer serverPort) {
                this.serverPort = serverPort;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public Integer getIndex() {
                return index;
            }

            public void setIndex(Integer index) {
                this.index = index;
            }
        }
    }
}
