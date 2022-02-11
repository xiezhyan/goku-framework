package top.zopx.starter.tools.tools.json;

import com.google.gson.Gson;
import top.zopx.starter.tools.tools.json.impl.GJson;

/**
 * 默认Json方法
 *
 * @author xiezhongyan
 * @email xiezhyan@126.com
 * @date 2022/2/11
 */
public final class JsonUtil {

    private IJson json;

    private JsonUtil() {
        json = new GJson(new Gson());
    }

    private static class Holder {
        public static final JsonUtil INSTANCE = new JsonUtil();
    }

    public static JsonUtil getInstance() {
        return Holder.INSTANCE;
    }

    public IJson getJson() {
        return json;
    }
}
