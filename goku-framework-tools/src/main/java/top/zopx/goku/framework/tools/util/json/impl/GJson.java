package top.zopx.goku.framework.tools.util.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import top.zopx.goku.framework.tools.util.json.IJson;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 俗世游子
 * @date 2021/6/7
 * @email xiezhyan@126.com
 */
public class GJson implements IJson {

    private final Gson gson;

    public GJson(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> String toJson(T obj) {
        return gson.toJson(obj);
    }

    @Override
    public <T> T toObject(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    @Override
    public <T> List<T> toObjList(String json, Class<T[]> clazz) {
        T[] ts = gson.fromJson(json, clazz);
        return Arrays.asList(ts);
    }

    @Override
    public <K, V> Map<K, V> toMap(String json) {
        Type type = new TypeToken<Map<K, V>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
