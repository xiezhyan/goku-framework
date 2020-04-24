package top.zopx.starter.tools.tools.json;

import com.alibaba.fastjson.JSON;

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

    public static String obj2Json(Object obj) {

        return JSON.toJSONString(obj);
    }

    public static <T> T json2Obj(String jsonStr, Class<T> clazz) {

        return JSON.parseObject(jsonStr, clazz);
    }

    public static <T> List<T> json2ObjList(String jsonStr, Class<T> clazz) {

        return JSON.parseArray(jsonStr, clazz);
    }

    public static Map obj2Map(Object obj) {
        return (Map) JSON.toJSON(obj);
    }
}
