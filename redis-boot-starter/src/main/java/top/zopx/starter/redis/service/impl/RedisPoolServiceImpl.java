package top.zopx.starter.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import top.zopx.starter.redis.service.RedisPoolService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * top.zopx.starter.redis.service.impl.RedisPoolServiceImpl
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */
public class RedisPoolServiceImpl implements RedisPoolService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, Long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void putHash(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public void putHash(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public Object getHash(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public List<Object> getHash(String key, String... fields) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(fields));
    }

    @Override
    public Map<Object, Object> getHash(String key) {

        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Long getHashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    @Override
    @SuppressWarnings("ALL")
    public void deleteHash(String key, String... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    @Override
    public Boolean hasHash(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    @Override
    public Long addHash(String key, String field, Long incr) {
        return redisTemplate.opsForHash().increment(key, field, incr);
    }

    @Override
    public Double addHash(String key, String field, Double incr) {
        return redisTemplate.opsForHash().increment(key, field, incr);
    }

    @Override
    public Object evalLuaText(String script, List<String> keys, Object... args) {
        DefaultRedisScript<Object> redisScript = new DefaultRedisScript<>();

        redisScript.setResultType(Object.class);
        redisScript.setScriptText(script);

        return redisTemplate.execute(redisScript, keys, args);
    }

    @Override
    public Object evalLuaPath(String path, List<String> keys, Object... args) {
        DefaultRedisScript<Object> redisScript = new DefaultRedisScript<>();

        redisScript.setResultType(Object.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));

        return redisTemplate.execute(redisScript, keys, args);
    }
}
