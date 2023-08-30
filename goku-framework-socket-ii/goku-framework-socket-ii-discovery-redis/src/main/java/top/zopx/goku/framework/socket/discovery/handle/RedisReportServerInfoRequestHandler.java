package top.zopx.goku.framework.socket.discovery.handle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import top.zopx.goku.framework.socket.core.circuit.Context;
import top.zopx.goku.framework.socket.core.circuit.chain.ReportServerInfoRequestHandler;
import top.zopx.goku.framework.socket.core.circuit.chain.RequestHandler;
import top.zopx.goku.framework.socket.core.entity.ReportServerInfo;
import top.zopx.goku.framework.socket.core.pubsub.IReport;
import top.zopx.goku.framework.socket.discovery.Redis;
import top.zopx.goku.framework.socket.discovery.constant.RedisKeyEnum;
import top.zopx.goku.framework.socket.discovery.pubsub.RedisPub;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

/**
 * 基于Redis上报服务信息
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 13:55
 */
public class RedisReportServerInfoRequestHandler implements RequestHandler {

    @Override
    public void handleRequest(Context context) {
        new ReportServerInfoRequestHandler(
                new RedisReport()
        ).handleRequest(context);
    }
}

class RedisReport implements IReport {
    @Override
    public void report(ReportServerInfo serverInfo) {
        try (Jedis redisCache = Redis.getMain()) {
            // 缓存关键字
            final String redisKey =
                    RedisKeyEnum.KEY_SERVER_X_PREFIX.format(serverInfo.getServerId());

            // 缓存过期时间
            final int expireTime = 10;

            // 设置缓存数据并在合适的时候过期
            redisCache.set(
                    redisKey,
                    GsonUtil.getInstance().toJson(serverInfo),
                    SetParams.setParams().ex(expireTime)
            );

            // 发布新服务器 Id
            RedisPub.getInstance()
                    .pub(
                            RedisKeyEnum.REGISTER_SERVER.getKey(),
                            String.valueOf(serverInfo.getServerId())
                    );
        }
    }
}