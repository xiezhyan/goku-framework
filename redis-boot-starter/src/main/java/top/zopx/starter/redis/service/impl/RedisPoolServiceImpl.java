package top.zopx.starter.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import top.zopx.starter.redis.service.RedisPoolService;
import top.zopx.starter.tools.exceptions.BusException;

import java.io.IOException;
import java.util.*;
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
    public void putListL(String key, Object... values) {
        redisTemplate.opsForList()
                .leftPushAll(key, values);
    }

    @Override
    public void putListR(String key, Object... values) {
        redisTemplate.opsForList()
                .rightPushAll(key, values);
    }

    @Override
    public List<Object> getList(String key, Long start, Long end) {
        return redisTemplate.opsForList()
                .range(key, start, end);
    }

    @Override
    public Long getListSize(String key) {
        return redisTemplate.opsForList()
                .size(key);
    }

    @Override
    public void deleteList(String key, Long start, Long end) {
        redisTemplate.opsForList()
                .trim(key, start, end);
    }

    @Override
    public void deleteListByItem(String key, Object item) {
        redisTemplate.opsForList()
                .remove(key, 0L, item);
    }

    @Override
    public void putSet(String key, Object... values) {
        redisTemplate.opsForSet()
                .add(key, values);
    }

    @Override
    public Long getSetSize(String key) {
        return redisTemplate.opsForSet()
                .size(key);
    }

    @Override
    public Boolean hasItemBySet(String key, Object item) {
        return redisTemplate.opsForSet()
                .isMember(key, item);
    }

    @Override
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet()
                .members(key);
    }

    @Override
    public void deleteSetByItem(String key, Object... values) {
        redisTemplate.opsForSet()
                .remove(key, values);
    }

    @Override
    public void putZSet(String key, Double score, Object value) {
        redisTemplate.opsForZSet()
                .add(key, value, score);
    }

    @Override
    public Set<Object> getZSet(String key, long start, long end, Order order) {
        switch (order) {
            case ASC:
                return redisTemplate.opsForZSet()
                        .range(key, start, end);
            case DESC:
                return redisTemplate.opsForZSet()
                        .reverseRange(key, start, end);
            default:
                return Collections.emptySet();
        }
    }

    @Override
    public Long getZSetSize(String key) {
        return redisTemplate.opsForZSet()
                .zCard(key);
    }

    @Override
    public Long hasItemByZSet(String key, Object item) {
        return redisTemplate.opsForZSet()
                .rank(key, item);
    }

    @Override
    public Double getScopeByItem(String key, Object value) {
        return redisTemplate.opsForZSet()
                .score(key, value);
    }

    @Override
    public void deleteZSet(String key, long start, long end) {
        redisTemplate.opsForZSet()
                .removeRange(key, start, end);
    }

    @Override
    public void deleteZSet(String key, double start, double end) {
        redisTemplate.opsForZSet()
                .removeRangeByScore(key, start, end);
    }

    @Override
    public void deleteZSetByItem(String key, Object... objects) {
        redisTemplate.opsForZSet()
                .remove(key, objects);
    }

    @Override
    public Double updateScopeByItem(String key, double score, Object member) {
        return redisTemplate.opsForZSet()
                .incrementScore(key, member, score);
    }

    @Override
    public void putHyperLog(String key, Object... value) {
        redisTemplate.opsForHyperLogLog()
                .add(key, value);
    }

    @Override
    public Long getHyperLogSize(String key) {
        return redisTemplate.opsForHyperLogLog()
                .size(key);
    }

    @Override
    public Long hyperMerge(String key, String other) {
        return redisTemplate.opsForHyperLogLog()
                .union(key, other);
    }

    @Override
    public void putBit(String key, long offset, Boolean value) {
        redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
            redisConnection.setBit(key.getBytes(), offset, value);
            return offset;
        });
    }

    @Override
    public Boolean getBit(String key, long offset) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection ->
                redisConnection.getBit(key.getBytes(), offset));
    }

    @Override
    public Long getBitSize(String key) {
        return redisTemplate.execute((RedisCallback<Long>) redisConnection
                -> redisConnection.bitCount(key.getBytes()));
    }

    @Override
    public void putGeo(String key, Map<Object, Point> map) {
        redisTemplate.opsForGeo()
                .add(key, map);
    }

    @Override
    public void putGeo(String key, Point point, Object member) {
        redisTemplate.opsForGeo()
                .add(key, point, member);
    }

    @Override
    public List<Point> getGeo(String key, Object... member) {
        return redisTemplate.opsForGeo()
                .position(key, member);
    }

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> getGeo(String key,
                                                                   Object member,
                                                                   double radius,
                                                                   Long limit,
                                                                   Order order) {
        Distance distance = new Distance(radius);

        return redisTemplate.opsForGeo()
                .radius(key,
                        member,
                        distance,
                        getGeoRadiusCommandArgs(limit, order));
    }


    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> getGeo(String key,
                                                                   Point center,
                                                                   double radius,
                                                                   Long limit,
                                                                   Order order) {
        Circle circle = new Circle(center, radius);

        return redisTemplate.opsForGeo()
                .radius(key,
                        circle,
                        getGeoRadiusCommandArgs(limit, order));
    }

    @Override
    public Distance getDist(String key, Object member1, Object member2) {
        return redisTemplate.opsForGeo()
                .distance(key, member1, member2);
    }

    @Override
    public void deleteKey(String... key) {
        redisTemplate.delete(Arrays.asList(key));
    }

    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.execute((RedisCallback<Set<String>>) redisConnection -> {
            Set<String> keys = new HashSet<>();
            Cursor<byte[]> cursor = redisConnection.scan(ScanOptions.scanOptions().count(100).match(pattern).build());
            while (cursor.hasNext()) {
                keys.add(new String(cursor.next()));
            }
            try {
                cursor.close();
            } catch (IOException e) {
                throw new BusException("cursor 关闭出现异常: " + e.getMessage());
            }
            return keys;
        });
    }

    @Override
    public void deleteKey(String pattern) {
        redisTemplate.delete(keys(pattern));
    }

    @Override
    public void expire(String key, long time, long min) {

        if (0L >= min) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return;
        }

        String lua = "local time = tonumber(redis.call('TTL', KEYS[1]) or '0') " +
                "if time < ARGV[2] then " +
                "redis.call('EXPIRE', KEYS[1], ARGV[1])" +
                "end";

        evalLuaText(lua, Collections.singletonList(key), time, min);
    }

    @Override
    public Long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public Long incr(String key) {
//        RedisAtomicLong atomicLong = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
//        return atomicLong.incrementAndGet();
        return redisTemplate.opsForValue()
                .increment(key);
    }

    @Override
    public Long incr(String key, Long step) {
//        RedisAtomicLong atomicLong = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
//        return atomicLong.getAndAdd(step);
        return redisTemplate.opsForValue()
                .increment(key, step);
    }

    @Override
    public Long decr(String key) {
//        RedisAtomicLong atomicLong = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
//        return atomicLong.decrementAndGet();
        return redisTemplate.opsForValue()
                .decrement(key);
    }

    @Override
    public Long decr(String key, Long step) {
//        RedisAtomicLong atomicLong = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
//        return atomicLong.getAndAdd(-step);
        return redisTemplate.opsForValue()
                .decrement(key, step);
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


    private RedisGeoCommands.GeoRadiusCommandArgs getGeoRadiusCommandArgs(Long limit, Order order) {

        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .limit(limit);

        switch (order) {
            case ASC:
                geoRadiusCommandArgs = geoRadiusCommandArgs.sortAscending();
                break;
            case DESC:
                geoRadiusCommandArgs = geoRadiusCommandArgs.sortDescending();
                break;
        }
        return geoRadiusCommandArgs;
    }
}
