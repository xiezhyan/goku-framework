package top.zopx.starter.redis.service;

import java.util.List;
import java.util.Map;

/**
 * top.zopx.starter.redis.service.RedisPoolService
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */
public interface RedisPoolService {


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


    /*** eval ***/
    /**
     * eval执行
     *
     * @param script 执行脚本
     * @param keys   需要验证的key
     * @param args   传递的参数
     * @return 返回结果
     */
    Object evalLuaText(String script, List<String> keys, Object... args);

    /**
     * eval执行
     *
     * @param path 脚本文件的路径
     * @param keys 需要验证的key
     * @param args 传递的参数
     * @return 返回结果
     */
    Object evalLuaPath(String path, List<String> keys, Object... args);

}
