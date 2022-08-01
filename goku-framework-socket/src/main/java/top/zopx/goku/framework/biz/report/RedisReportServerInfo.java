package top.zopx.goku.framework.biz.report;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import top.zopx.goku.framework.biz.redis.RedisPublish;
import top.zopx.goku.framework.biz.redis.RedisCache;
import top.zopx.goku.framework.cluster.constant.PublishCons;
import top.zopx.goku.framework.cluster.constant.RedisKeyCons;
import top.zopx.goku.framework.cluster.entity.IServerInfo;
import top.zopx.goku.framework.tools.util.json.JsonUtil;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 20:07
 */
public class RedisReportServerInfo implements IReportServerInfo{

    private final RedisPublish redisPublish = new RedisPublish();

    @Override
    public void report(IServerInfo.ServerInfo newInfo) {
        try (Jedis redisCache = RedisCache.getServerCache()) {
            // 缓存关键字
            final String redisKey = RedisKeyCons.SERVER_X_PREFIX + newInfo.getServerId();
            // 缓存过期时间
            final int expireTime = 10;

            // 设置缓存数据并在合适的时候过期
            redisCache.set(redisKey, JsonUtil.getInstance().getJson().toJson(newInfo), SetParams.setParams().ex(expireTime));

            // 发布新服务器 Id
            redisPublish.pub(PublishCons.REGISTER_SERVER, String.valueOf(newInfo.getServerId()));
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
