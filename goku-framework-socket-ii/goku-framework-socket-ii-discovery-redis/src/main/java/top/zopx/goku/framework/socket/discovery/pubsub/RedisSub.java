package top.zopx.goku.framework.socket.discovery.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import top.zopx.goku.framework.socket.core.pubsub.ISubscribe;
import top.zopx.goku.framework.socket.discovery.Redis;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 13:37
 */
@SuppressWarnings("all")
public class RedisSub implements ISubscribe {

    private RedisSub() {}

    private static class Holder {
        private static final RedisSub INS = new RedisSub();
    }
    public static RedisSub getInstance() {
        return Holder.INS;
    }

    private static final ThreadPoolExecutor ES =
            new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(),
                    r -> new Thread(r, "goku-redis-sub"));

    @Override
    public void subscribe(String[] chArray, ISubscribe h) {
        if (null == chArray ||
                chArray.length == 0) {
            throw new BusException("chArray is null or empty", IBus.ERROR_CODE);
        }

        if (null == h) {
            throw new BusException("h is null or empty", IBus.ERROR_CODE);
        }

        // 记录日志信息
        LOG.debug(
                "开启订阅, channelArray = [ {} ]",
                Arrays.toString(chArray)
        );

        ES.submit(() -> {
            try (Jedis redis = Redis.getMain()) {
                redis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String strMsg) {
                        onMsg(channel, strMsg, h);
                    }
                }, chArray);
            }
        });
    }

    private void onMsg(String ch, String strMsg, ISubscribe h) {
        if (null == ch ||
                null == strMsg ||
                null == h) {
            return;
        }

        try {
            // 处理消息
            h.onMsg(ch, strMsg);
        } catch (Exception ex) {
            // 记录错误日志
            LOG.error(ex.getMessage(), ex);
        }
    }
}
