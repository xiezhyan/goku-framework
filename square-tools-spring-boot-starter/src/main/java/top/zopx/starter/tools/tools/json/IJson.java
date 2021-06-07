package top.zopx.starter.tools.tools.json;

import java.util.List;
import java.util.Map;

/**
 * json通用方法
 *
 * @author xiezhongyan
 * @date 2021/6/7
 * @email xiezhyan@126.com
 */
public interface IJson {

    /**
     * 序列化对象为JSON
     *
     * @param obj 需要序列化的对象
     * @param <T> 类型
     * @return JSON字符串
     */
    <T> String toJson(T obj);

    /**
     * 反序列化JSON为对象
     *
     * @param json  需要反序列化的JSON字符串
     * @param clazz 需要返回的对象类的class
     * @param <T>   类型
     * @return 返回对象
     */
    <T> T toObject(String json, Class<T> clazz);

    /**
     * 转成List类型对象
     *
     * @param json  需要反序列化的JSON字符串
     * @param clazz 需要返回的对象类的class
     * @param <T>   类型
     * @return 返回的List对象
     */
    <T> List<T> toObjList(String json, Class<T> clazz);

    /**
     * 将字符串转成Map
     *
     * @param json 需要转换的JSON字符串
     * @param <K>  Key
     * @param <V>  Value
     * @return 返回Map对象
     */
    <K, V> Map<K, V> toMap(String json);
}
