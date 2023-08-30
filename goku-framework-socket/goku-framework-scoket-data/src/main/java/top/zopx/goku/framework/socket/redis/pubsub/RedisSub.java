package top.zopx.goku.framework.socket.redis.pubsub;

import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import top.zopx.goku.framework.socket.core.pubsub.ISubscribe;
import top.zopx.goku.framework.socket.redis.Redis;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 订阅
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public final class RedisSub implements ISubscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSub.class);

    private static final ThreadPoolExecutor ES =
            new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(),
                    r -> new Thread(r, "goku-redis-sub"));

    /**
     * 订阅指定频道
     *
     * @param chArray 频道数组
     * @param h       消息处理器
     * @throws IllegalArgumentException if null == chArray or chArray.length <= 0
     * @throws IllegalArgumentException if null == h
     */
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
        LOGGER.info(
                "开启订阅, channelArray = [ {} ]",
                (Object) chArray
        );

        ES.submit(() -> {
            try (Jedis redis = Redis.get()) {
                redis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String strMsg) {
                        onMsg(channel, strMsg, h);
                    }
                }, chArray);
            } catch (Exception ex) {
                // 记录错误日志
                LOGGER.error(ex.getMessage(), ex);
            }
        });
    }

    /**
     * 当接到消息时
     *
     * @param ch     频道
     * @param strMsg 字符串消息
     * @param h      消息处理器
     */
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
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public static void main(String[] args) {
        final SubscribeGroup subscribeGroup = new SubscribeGroup();
        subscribeGroup.add(new ISubscribe() {
            @Override
            public void onMsg(String channel, String msg) {
                // 服务发布
                if (!Objects.equals(channel, "registry:server")) {
                    return;
                }
                // 服务ID
                int serverId = Integer.parseInt(msg);
                // 从Redis中取出对应的信息
                // 加入到服务器集合中
            }
        });
        ISubscribe subscribe = new RedisSub();
        subscribe.subscribe(new String[]{"registry:server"}, subscribeGroup);
    }
}
