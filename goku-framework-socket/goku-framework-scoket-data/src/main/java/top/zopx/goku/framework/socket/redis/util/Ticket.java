package top.zopx.goku.framework.socket.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import top.zopx.goku.framework.socket.redis.Redis;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;

import java.util.UUID;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:59
 */
public final class Ticket {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ticket.class);

    private Ticket() {
    }

    public static String getTicket(long userId) {
        if (userId <= 0) {
            return null;
        }
        // 创建票据
        final String newTicket = UUID.randomUUID().toString();
        // 票据关键字
        final String redisKey = RedisKeyEnum.TICKET_X_PREFIX.format(newTicket);

        try (Jedis redisCache = Redis.get()) {
            // 将票据保存到 Redis 20 秒有效期
            redisCache.set(
                    redisKey,
                    String.valueOf(userId),
                    SetParams.setParams().ex(20)
            );
            return newTicket;
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }

        return null;
    }
}
