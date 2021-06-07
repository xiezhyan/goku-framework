package top.zopx.starter.tools.tools.json.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import top.zopx.starter.tools.tools.json.IJson;

import java.util.List;
import java.util.Map;


/**
 * FastJson
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public enum FJsonUtil implements IJson {

    /**
     * 单例
     */
    INSTANCE,
    ;

    /**
     * 序列化对象为JSON
     *
     * @param obj 需要序列化的对象
     * @return JSON字符串
     */
    @Override
    public <T> String toJson(T obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 反序列化JSON为对象
     *
     * @param json  需要反序列化的JSON字符串
     * @param clazz 需要返回的对象类的class
     * @return 返回对象
     */
    @Override
    public <T> T toObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 转成List类型对象
     *
     * @param json  需要反序列化的JSON字符串
     * @param clazz 需要返回的对象类的class
     * @return 返回的List对象
     */
    @Override
    public <T> List<T> toObjList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 将字符串转成Map
     *
     * @param json 需要转换的JSON字符串
     * @return 返回Map对象
     */
    @Override
    public <K, V> Map<K, V> toMap(String json) {
        return JSON.parseObject(json, new TypeReference<Map<K, V>>() {
        });
    }
}
