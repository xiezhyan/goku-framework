package top.zopx.starter.tools.tools.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;


/**
 * version: 方便json操作
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class JsonUtil {

    private JsonUtil() {
    }

    /**
     * 将对象转成字符串
     */
    public static String obj2Json(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 将json字符串转成对象
     */
    public static <T> T json2Obj(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * 将JSON转成Java对象
     */
    public static <T> T json2Object (JSON json, Class<T> clazz) {
        return JSON.toJavaObject(json, clazz);
    }

    /**
     * 转成List类型对象
     */
    public static <T> List<T> json2ObjList(String jsonStr, Class<T> clazz) {
        return JSON.parseArray(jsonStr, clazz);
    }

    /**
     * 将字符串转成Map
     */
    public static <K, V> Map<K, V> json2Map (String entity) {
        return JSON.parseObject(entity,new TypeReference<Map<K,V>>() {});
    }
}
