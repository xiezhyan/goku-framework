package top.zopx.starter.tools.tools.json.impl;

import top.zopx.starter.tools.tools.json.IJson;

import java.util.List;
import java.util.Map;

/**
 * @author xiezhongyan
 * @date 2021/6/7
 * @email xiezhyan@126.com
 */
public enum GJsonUtil implements IJson {
    /**
     * 单例
     */
    INSTANCE,
    ;

    @Override
    public <T> String toJson(T obj) {
        return "";
    }

    @Override
    public <T> T toObject(String json, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> List<T> toObjList(String json, Class<T> clazz) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> toMap(String json) {
        return null;
    }
}
