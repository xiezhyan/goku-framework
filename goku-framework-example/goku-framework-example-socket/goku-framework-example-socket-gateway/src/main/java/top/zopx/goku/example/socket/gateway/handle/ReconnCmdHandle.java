package top.zopx.goku.example.socket.gateway.handle;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import top.zopx.goku.example.socket.common.constant.Constant;
import top.zopx.goku.example.socket.common.util.ClientChannelGroup;
import top.zopx.goku.example.socket.common.util.IdUtil;
import top.zopx.goku.example.socket.common.util.UKey;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.example.socket.gateway.codec.SemiClientMsgFinished;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.biz.redis.RedisCache;
import top.zopx.goku.framework.cluster.constant.PublishCons;
import top.zopx.goku.framework.cluster.constant.RedisKeyCons;

import java.util.concurrent.TimeUnit;

/**
 * 客户端消息处理器
 *
 * @author 俗世游子
 */
public class ReconnCmdHandle extends ChannelInboundHandlerAdapter {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReconnCmdHandle.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (null == ctx ||
                !(msg instanceof SemiClientMsgFinished semiClientMsg)) {
            // 如果接到的不是客户端半成品消息,
            super.channelRead(ctx, msg);
            return;
        }

        // 如果不是重连命令，直接return
        int msgCode = semiClientMsg.getMsgCode();
        if (Common.CommonDef._ReconnRequest_VALUE != msgCode) {
            super.channelRead(ctx, msg);
            return;
        }

        Common.ReconnRequest reconnRequest = Common.ReconnRequest.parseFrom(semiClientMsg.getData());

        long userId = reconnRequest.getUserId();

        if (!UKey.vertify(userId, reconnRequest.getUkey(), reconnRequest.getUkeyExpireAt())) {
            ctx.disconnect();
            return;
        }

        if (renewConn(ctx, userId, null)) {
            // 构建重连结果
            Common.ReconnResponse response = Common.ReconnResponse.newBuilder()
                    .setOk(true)
                    .setUserId(userId)
                    .setUkey(reconnRequest.getUkey())
                    .setUkeyExpire(reconnRequest.getUkeyExpireAt())
                    .build();
            // 发送重连结果
            ctx.writeAndFlush(response);
        }
    }

    public static boolean renewConn(ChannelHandlerContext ctx, long userId, Jedis jedis) {
        if (null == ctx ||
                userId <= 0) {
            return false;
        }

        if (null == jedis) {
            try (Jedis newCache = RedisCache.getServerCache()) {
                // 获取新的 Redis 之后重新执行
                return renewConn(
                        ctx, userId, newCache
                );
            }
        }

        try {
            String userInfoKey = RedisKeyCons.KEY_USER_INFO.format(userId);
            // 如果已经标记所在代理服务器,
            // 那么就删除这个标记!
            long delOther = jedis.hdel(
                    userInfoKey,
                    Constant.USER_AT_PROXY_SERVER_ID,
                    Constant.USER_REMOTE_SESSION_ID
            );

            if (delOther > 0) {
                // 如果删除成功,
                // 则说明用户已经在其他代理服务器上建立连接,
                // 通过 RECONNECT_USER_NOTICE 通知其断开连接!
                // 但是当前连接不要断开...
                try (Jedis redisPubSub = RedisCache.getPubsub()) {
                    // 无论是否连接到其他代理服务器,
                    // 都通知其他代理服务器断开客户端连接...
                    JsonObject joNotice = new JsonObject();
                    joNotice.addProperty("newGatewayId", GatewayApp.getServerId());
                    joNotice.addProperty("userId", userId);

                    redisPubSub.publish(
                            PublishCons.CONNECTION_TRANSFER_NOTICE,
                            joNotice.toString()
                    );
                }

                LOGGER.warn("用户已经在其他代理服务器上建立连接, 需要断开连接! userId = {}", userId);
            }

            // 获取本地服务器上的连接
            Channel oldCh = ClientChannelGroup.getChannelByUserId(userId);

            if (null != oldCh &&
                    oldCh != ctx.channel()) {
                // 如果本服务器上已有不同连接,
                LOGGER.warn("连接转移! userId = {}", userId);
                ClientMsgHandle msgHandler = oldCh.pipeline().get(ClientMsgHandle.class);
                msgHandler.setConnAlreadyTransfer(true);

                Common.KickOutUserResponse response = Common.KickOutUserResponse.newBuilder()
                        .setReason("成功连接到其他服务器")
                        .build();

                oldCh.writeAndFlush(response);
                oldCh.disconnect().sync().await(2, TimeUnit.SECONDS);
            }

            LOGGER.info("检票成功, 设置用户 Id = {}", userId);

            // 设置用户 Id
            IdUtil.putUserId(ctx, userId);
            ClientChannelGroup.relative(userId, IdUtil.getSessionId(ctx));

            // 标记所在代理服务器 Id
            jedis.hset(
                    userInfoKey,
                    Constant.USER_AT_PROXY_SERVER_ID,
                    String.valueOf(GatewayApp.getServerId())
            );

            // 标记远程会话 Id
            jedis.hset(
                    userInfoKey,
                    Constant.USER_REMOTE_SESSION_ID,
                    String.valueOf(IdUtil.getSessionId(ctx))
            );

            return true;
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }

        return false;
    }
}
