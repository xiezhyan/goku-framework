package top.zopx.goku.framework.socket.discovery.handle;

import com.google.gson.JsonElement;
import top.zopx.goku.framework.socket.tools.circuit.Context;
import top.zopx.goku.framework.socket.tools.circuit.chain.RequestHandler;
import top.zopx.goku.framework.socket.discovery.Redis;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.util.Map;

/**
 * 对Redis进行配置
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 13:32
 */
public class RedisConfigureInitRequestHandler implements RequestHandler {
    @Override
    public void handleRequest(Context context) {
        Object redis = context.attr("redis");

        if (redis instanceof Map<?, ?> redisMap) {
            JsonElement jsonEle = GsonUtil.getInstance()
                    .getGson().toJsonTree(redisMap);
            Redis.init(jsonEle.getAsJsonObject());
            LOG.debug("。。。redis初始化完成。。。");
        }
    }
}
