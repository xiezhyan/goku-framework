package top.zopx.goku.framework.socket.discovery.pubsub;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import top.zopx.goku.framework.socket.core.pubsub.IPublish;
import top.zopx.goku.framework.socket.discovery.Redis;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 13:35
 */
public class RedisPub implements IPublish {

    private RedisPub() {}
    private static class Holder {
        private static final RedisPub INS = new RedisPub();
    }
    public static RedisPub getInstance() {
        return Holder.INS;
    }

    @Override
    public void pub(String topic, String msg) {
        if (StringUtils.isBlank(topic) || StringUtils.isBlank(msg)) {
            return;
        }

        LOG.debug("publish topic = {}, msg = {}", topic, msg);

        try(Jedis jedis = Redis.getMain()) {
            jedis.publish(topic, msg);
        }
    }
}
