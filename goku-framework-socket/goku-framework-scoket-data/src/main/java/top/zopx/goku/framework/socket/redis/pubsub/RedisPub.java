package top.zopx.goku.framework.socket.redis.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import top.zopx.goku.framework.socket.core.pubsub.IPublish;
import top.zopx.goku.framework.socket.redis.Redis;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/26 19:33
 */
public class RedisPub implements IPublish {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisPub.class);

    @Override
    public void pub(String topic, String msg) {
        if (null == topic ||
                null == msg) {
            return;
        }

        LOGGER.debug("pub, topic = {}, msg = {}", topic, msg);

        try (Jedis jedis = Redis.get()) {
            jedis.publish(topic, msg);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
