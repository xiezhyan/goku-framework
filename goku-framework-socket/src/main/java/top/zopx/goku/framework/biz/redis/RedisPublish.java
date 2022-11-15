package top.zopx.goku.framework.biz.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import top.zopx.goku.framework.biz.pubsub.IPublish;

/**
 * 发布
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public final class RedisPublish implements IPublish {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisPublish.class);

    /**
     * 发布
     *
     * @param channel 频道
     * @param msg     字符串消息
     */
    @Override
    public void pub(String channel, String msg) {
        if (null == channel ||
                null == msg) {
            return;
        }

        try (Jedis jedis = RedisCache.getPubsub()) {
            jedis.publish(channel, msg);
        } catch (Exception ex) {
            ex.printStackTrace();
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
