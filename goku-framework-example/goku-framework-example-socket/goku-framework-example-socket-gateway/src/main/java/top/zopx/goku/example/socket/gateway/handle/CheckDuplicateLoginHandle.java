package top.zopx.goku.example.socket.gateway.handle;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import top.zopx.goku.example.socket.common.async.AsyncOperationProcessorSingleton;
import top.zopx.goku.example.socket.common.constant.Constant;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.example.socket.gateway.codec.SemiClientMsgFinished;
import top.zopx.goku.example.socket.proto.auth.Auth;
import top.zopx.goku.framework.socket.netty.util.ChannelUtil;
import top.zopx.goku.framework.socket.netty.util.IdUtil;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;
import top.zopx.goku.framework.socket.redis.lock.DLock;
import top.zopx.goku.framework.socket.redis.Redis;

/**
 * 重复登录检查
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/22 21:51
 */
public class CheckDuplicateLoginHandle extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckDuplicateLoginHandle.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (null == ctx || !(msg instanceof SemiClientMsgFinished)) {
            super.channelRead(ctx, msg);
            return;
        }

        SemiClientMsgFinished semiClientMsg = (SemiClientMsgFinished) msg;
        if (Auth.AuthDef._LoginRequest_VALUE != semiClientMsg.getMsgCode()) {
            // 不是登录消息，不需要进行重复登录判断
            super.channelRead(ctx, msg);
            return;
        }
        checkDuplicateLogin(ctx, msg);
    }

    private void checkDuplicateLogin(ChannelHandlerContext ctx, Object msg) {
        /**
         * 1. 先通过本地Map进行判断是否存在，如果不存在，就不需要重新处理，继续进行后续操作
         * 2. 在gateway_N中判断当前UserID是否存在，如果不存在，说明当前不属于重复登录
         * 3. 在user_N中判断当前GatewayID是否已经写入，如果不存在，说明不属于重复登录
         */
        // 取出其中的sessionId
        final Integer sessionId = IdUtil.getSessionId(ctx);
        final Channel channel = ChannelUtil.getChannelBySessionId(sessionId);

        if (null == channel) {
            // 无需判断
            return;
        }

        final Long userId = IdUtil.getUserId(ctx);
        if (null == userId || userId <= 0) {
            // 无需判断
            return;
        }

        final Channel existsChannel = ChannelUtil.getChannelByUserId(userId);
        if (null != existsChannel) {
            try {
                LOGGER.warn(
                        "断开已有连接, userId = {}",
                        userId
                );

                existsChannel.disconnect().sync();
                channel.disconnect().sync();
                return;
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
                Thread.currentThread().interrupt();
            }
        }

        AsyncOperationProcessorSingleton.getInstance().process(
                userId.intValue(),
                () -> {
                    try (DLock lock = DLock.newLock("checkDuplicateLogin?userId = " + userId)) {
                        if (null == lock || !lock.tryLock(5000L)) {
                            LOGGER.warn("分布式锁加锁失败!, userId = {}", userId);
                            channel.disconnect().sync();
                            return;
                        }

                        if (checkDuplicateLoginInRedis(userId)) {
                            LOGGER.info("绑定用户 Id, sessionId = {}, userId = {}", sessionId, userId);
                            IdUtil.attachUserId(channel, userId);
                        } else {
                            LOGGER.warn("发生重复登录, 断开连接! userId = {}", userId);
                            channel.disconnect().sync();
                        }
                    } catch (Exception ex) {
                        LOGGER.error(ex.getMessage(), ex);
                        Thread.currentThread().interrupt();
                    }
                },
                ctx.executor(),
                () -> {
                    if (channel.isOpen()) {
                        ctx.fireChannelRead(msg);
                    }
                }
        );

    }

    /**
     * 通过 Redis 检查是否有重复登录
     *
     * @param userId 用户 Id
     * @return true = 检查结果成功，没有重复登录, false = 检查结果失败，有重复登录
     */
    private boolean checkDuplicateLoginInRedis(Long userId) {
        if (userId <= 0) {
            return false;
        }

        try (Jedis jedis = Redis.get("server")) {
            // 先在用户信息中判断是否存在
            String userKey = RedisKeyEnum.KEY_USER_INFO.format(userId);
            String gatewayListKey = RedisKeyEnum.GATEWAY_USER_LIST.format(GatewayApp.getServerId());
            final String strUserAtGatewayServerId = jedis.hget(userKey, Constant.USER_AT_PROXY_SERVER_ID);
            if (StringUtils.isBlank(strUserAtGatewayServerId)) {
                // 不存在
                final long result = jedis.hset(
                        userKey,
                        Constant.USER_AT_PROXY_SERVER_ID,
                        String.valueOf(GatewayApp.getServerId())
                );
                if (result <= 0) {
                    return false;
                }

                // 放到gateway_N中
                jedis.hset(gatewayListKey, String.valueOf(userId), "1");
                return true;
            }

            // 第一张表里有数据的情况,
            // 1、这个数据正好和当前网关服务器 Id 相同
            if (strUserAtGatewayServerId.equals(String.valueOf(GatewayApp.getServerId()))) {
                // 如果 Redis 里存的服务器 Id 跟当前服务器 Id 相同,
                // 那正好, 什么也不用做...
                return true;
            }

            // 2、这个数据和当前网关服务器 Id 不相同，就判断当前userId是否存在于gateway_N
            if (jedis.hexists(RedisKeyEnum.GATEWAY_USER_LIST.format(strUserAtGatewayServerId), String.valueOf(userId))) {
                // 最后一种情况是 Redis 里存的服务器 Id 跟当前服务器 Id 不同,
                // 那么就得通知对方: 放手吧...

                try (Jedis pubsub = Redis.get("server")) {
                    JsonObject jsonObj = new JsonObject();
                    jsonObj.addProperty("gatewayServerId", strUserAtGatewayServerId);
                    jsonObj.addProperty("userId", userId);
                    pubsub.publish(
                            RedisKeyEnum.DISCONNECT_DUPLICATE_LOGIN.getKey(),
                            jsonObj.toString()
                    );
                }
                return false;
            }

            // 3、如果目标网关服务器数据为空,
            // 那么当前网关服务器强制更新数据!
            jedis.hset(
                    userKey, Constant.USER_AT_PROXY_SERVER_ID, String.valueOf(GatewayApp.getServerId())
            );
            jedis.hset(
                    gatewayListKey, String.valueOf(userId), "1"
            );

            return true;
        }
    }
}
