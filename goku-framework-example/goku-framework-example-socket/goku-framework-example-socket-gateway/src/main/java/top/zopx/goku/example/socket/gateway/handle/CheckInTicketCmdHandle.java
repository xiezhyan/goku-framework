package top.zopx.goku.example.socket.gateway.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import top.zopx.goku.example.socket.gateway.codec.SemiClientMsgFinished;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;
import top.zopx.goku.framework.socket.redis.lock.DLock;
import top.zopx.goku.framework.socket.redis.Redis;

/**
 * 客户端消息处理器
 *
 * @author Mr.Xie
 */
public class CheckInTicketCmdHandle extends ChannelInboundHandlerAdapter {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckInTicketCmdHandle.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (null == ctx ||
                !(msg instanceof SemiClientMsgFinished)) {
            // 如果接到的不是客户端半成品消息,
            super.channelRead(ctx, msg);
            return;
        }

        SemiClientMsgFinished semiClientMsg = (SemiClientMsgFinished) msg;

        if (Common.CommonDef._CheckInTicketRequest_VALUE != semiClientMsg.getMsgCode()) {
            // 接收到的不是检票
            super.channelRead(ctx, msg);
            return;
        }

        Common.CheckInTicketRequest request = Common.CheckInTicketRequest.parseFrom(semiClientMsg.getData());

        // 如果是检票请求，才正常执行
        Long userId = request.getUserId();    // 用户ID
        String ticket = request.getTicket();  // 登录时生成
        handle(ctx, userId, ticket);
    }

    private void handle(ChannelHandlerContext ctx, Long userId, String ticket) {
        if (null == ctx) {
            return;
        }

        if (userId <= 0 || null == ticket || ticket.isEmpty()) {
            LOGGER.error("登陆票据为空, userId = {}, ticket = {}", userId, ticket);
            ctx.disconnect();
            return;
        }

        // 创建分布式锁
        try (DLock newLocker = DLock.newLock("check_in_ticket?user_id=" + userId)) {
            //
            // 在检票之前先加锁,
            // 避免多个代理服务器同时验证一张票据!
            // 如果一个玩家通过不同的客户端连接到不同的代理服务器,
            // 但是登陆身份是一样的,
            // 就会出现这个问题...
            if (null == newLocker ||
                    !newLocker.tryLock(5000)) {
                LOGGER.error("检票时加锁失败, userId = {}", userId);
                ctx.disconnect();
                return;
            }

            try (Jedis redisCache = Redis.get()) {
                // 获取期望值并清空数据
                String key = RedisKeyEnum.TICKET_X_PREFIX.format(ticket);
                String expectId = redisCache.get(key);
                redisCache.del(key);

                if (!String.valueOf(userId).equals(expectId)) {
                    LOGGER.error(
                            "检票失败, userId = {}",
                            userId
                    );
                    ctx.disconnect();
                    return;
                }

                if (ReconnCmdHandle.renewConn(ctx, userId, redisCache)) {
                    // 构建检票结果
                    Common.CheckInTicketResponse response = Common.CheckInTicketResponse.newBuilder()
                            .setUserId(userId)
                            .setSuccezz(true)
                            .build();
                    // 发送检票结果
                    ctx.writeAndFlush(response);
                }
            } catch (Exception ex) {
                // 记录错误日志
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }
}
