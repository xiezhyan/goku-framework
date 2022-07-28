package top.zopx.goku.framework.biz.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * 发布
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public final class Publish {

    private static final Logger LOGGER = LoggerFactory.getLogger(Publish.class);

    /**
     * 发布
     *
     * @param channel 频道
     * @param msg     字符串消息
     */
    public void pub(String channel, String msg) {
        if (null == channel ||
                null == msg) {
            return;
        }

        try (Jedis jedis = RedisCache.getPubsub()) {
            jedis.publish(channel, msg);
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
