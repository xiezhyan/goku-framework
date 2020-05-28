package top.zopx.starter.redis.service;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * top.zopx.starter.redis.service.RedisPoolService
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */
public interface RedisPoolService {

    static enum Order {
        ASC,
        DESC,
        ;
    }

    // --- String start
    void set(String key, Object value, Long time);

    void set(String key, Object value);

    Object get(String key);
    // --- String end

    // --- Hash start
    void putHash(String key, Map<String, Object> map);

    void putHash(String key, String field, Object value);

    Object getHash(String key, String field);

    List<Object> getHash(String key, String... fields);

    Map<Object, Object> getHash(String key);

    Long getHashSize(String key);

    void deleteHash(String key, String... fields);

    Boolean hasHash(String key, String field);

    Long addHash(String key, String field, Long incr);

    Double addHash(String key, String field, Double incr);
    // --- Hash end

    // --- List start
    void putListL(String key, Object... values);

    void putListR(String key, Object... values);

    List<Object> getList(String key, Long start, Long end);

    Long getListSize(String key);

    void deleteList(String key, Long start, Long end);

    void deleteListByItem(String key, Object item);

    Object getPopFLeft(String key);

    Object getPopFRight(String key);
    // --- List end

    // --- Set start
    void putSet(String key, Object... values);

    Long getSetSize(String key);

    Boolean hasItemBySet(String key, Object item);

    Set<Object> getSet(String key);

    void deleteSetByItem(String key, Object... values);
    // --- Set end

    // --- ZSet start
    void putZSet(String key, Double score, Object value);

    Set<Object> getZSet(String key, long start, long end, Order order);

    Long getZSetSize(String key);

    Long hasItemByZSet(String key, Object item);

    Double getScopeByItem(String key, Object value);

    void deleteZSet(String key, long start, long end);

    void deleteZSet(String key, double start, double end);

    void deleteZSetByItem(String key, Object... objects);

    Double updateScopeByItem(String key, double score, Object member);
    // --- ZSet end

    // --- HyperLogLog start
    void putHyperLog(String key, Object... value);

    Long getHyperLogSize(String key);

    Long hyperMerge(String key, String other);
    // --- HyperLogLog end

    // --- 位图 start
    void putBit(String key, long offset, Boolean value);

    Boolean getBit(String key, long offset);

    Long getBitSize(String key);
    // --- 位图 start

    // --- 位置信息 start

    /**
     * 批量保存地理位置
     */
    void putGeo(String key, Map<Object, Point> map);

    /**
     * 保存地位位置
     */
    void putGeo(String key, Point point, Object member);

    /**
     * 返回指定member的位置信息
     */
    List<Point> getGeo(String key, Object... member);

    /**
     * 根据指定的成员， 返回附近范围内的元素
     */
    GeoResults<RedisGeoCommands.GeoLocation<Object>> getGeo(String key,
                                                            Object member,
                                                            double radius,
                                                            Long limit,
                                                            Order order);

    /**
     * 根据指定的位置， 返回附近范围内的元素
     */
    GeoResults<RedisGeoCommands.GeoLocation<Object>> getGeo(String key,
                                                            Point center,
                                                            double radius,
                                                            Long limit,
                                                            Order order);

    /**
     * 返回指定成员间的距离
     */
    Distance getDist(String key, Object member1, Object member2);

    // --- 位置信息 end

    void deleteKey(String... key);

    Boolean hasKey(String key);

    Set<String> keys(String pattern);

    void deleteKey(String pattern);

    void expire(String key, long time, long min);

    Long ttl(String key);

    Long incr(String key);

    Long incr(String key, Long step);

    Long decr(String key);

    Long decr(String key, Long step);

    // --- eval start

    Object evalLuaText(String script, List<String> keys, Object... args);

    Object evalLuaPath(String path, List<String> keys, Object... args);
}
