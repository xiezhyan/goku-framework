package top.zopx.goku.framework.socket.redis.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import top.zopx.goku.framework.socket.core.entity.IServerInfo;
import top.zopx.goku.framework.socket.core.pubsub.IPublish;
import top.zopx.goku.framework.socket.core.pubsub.IReport;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;
import top.zopx.goku.framework.socket.redis.Redis;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/26 19:42
 */
public class RedisReport implements IReport {

    private final IPublish publish;

    public RedisReport(IPublish publish) {
        this.publish = publish;
    }

    @Override
    public void report(IServerInfo.ServerInfo serverInfo) {
        try (Jedis redisCache = Redis.get()) {
            // 缓存关键字
            final String redisKey = RedisKeyEnum.KEY_SERVER_X_PREFIX.format(serverInfo.getServerId());
            // 缓存过期时间
            final int expireTime = 10;

            // 设置缓存数据并在合适的时候过期
            redisCache.set(
                    redisKey,
                    GsonUtil.getInstance().toJson(serverInfo),
                    SetParams.setParams().ex(expireTime)
            );

            // 发布新服务器 Id
            publish.pub(
                    RedisKeyEnum.REGISTER_SERVER.getKey(),
                    String.valueOf(serverInfo.getServerId())
            );
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }

    }
}
