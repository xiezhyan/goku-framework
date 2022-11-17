package top.zopx.goku.example.socket.gateway.handle;

import com.google.gson.JsonObject;
import io.netty.channel.*;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import top.zopx.goku.example.socket.common.constant.RedisPubsubEnum;
import top.zopx.goku.example.socket.common.util.ClientChannelGroup;
import top.zopx.goku.framework.util.IdUtil;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.example.socket.gateway.codec.ClientMsgDecode;
import top.zopx.goku.example.socket.gateway.codec.ClientMsgEncode;
import top.zopx.goku.framework.biz.redis.RedisCache;
import top.zopx.goku.framework.netty.bind.factory.BaseDefaultChannelHandler;

import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.HandshakeComplete;

/**
 * 客户端消息处理器
 * @author 俗世游子
 */
public class ClientMsgHandle extends BaseDefaultChannelHandler {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMsgHandle.class);

    /**
     * HTTP 头前缀
     */
    private static final String HTTP_HEADER_PREFIX = "http_header::";

    private boolean connAlreadyTransfer;

    @Override
    protected ChannelHandler[] getChannelHandlerArray() {
        return new ChannelHandler[]{
                new ClientMsgDecode(),
                new ClientMsgEncode(),
                // ping处理
                new PingHandle(),
                // 重新连接处理
                new ReconnCmdHandle(),
                // 检票处理
                new CheckInTicketCmdHandle(),
                // 最后的检测
                new UserIdValidatorHandle(),
                // 内部消息转发
                new ClientMsgRoute()
        };
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端连入
        if (null == ctx ||
                null == ctx.channel()) {
            return;
        }

        LOGGER.info("有新的客户端接入");
        // ctx附着会话ID
        IdUtil.attachSessionId(ctx);
        // 并且将ctx添加到组中，随后校验之后需要将sessionId和userId进行绑定
        ClientChannelGroup.add(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 客户端断开连接
        if (null == ctx ||
                null == ctx.channel()) {
            return;
        }

        // 移除客户端信道
        ClientChannelGroup.remove(ctx);

        if (connAlreadyTransfer) {
            LOGGER.info("客户端连接已转移...");
            return;
        }

        // 业务处理
        LOGGER.info("有客户端下线");
        // 获取用户 Id
        long userId = IdUtil.getUserId(ctx);

        if (userId <= 0) {
            return;
        }

        // 获取会话 Id
        int sessionId = IdUtil.getSessionId(ctx);

        try (Jedis redisPubSub = RedisCache.getPubsub()) {
            // 构建下线的用户
            JsonObject offlineUserJsonObj = new JsonObject();
            offlineUserJsonObj.addProperty("gatewayServerId", GatewayApp.getServerId());
            offlineUserJsonObj.addProperty("remoteSessionId", sessionId);
            offlineUserJsonObj.addProperty("userId", userId);

            LOGGER.info(
                    "发布用户离线通知, gatewayServerId = {}, remoteSessionId = {}, userId = {}",
                    GatewayApp.getServerId(),
                    sessionId,
                    userId
            );

            // 记录日志信息
            redisPubSub.publish(
                    RedisPubsubEnum.OFFLINE_USER_NOTICE.name(),
                    offlineUserJsonObj.toString()
            );
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void userEventTriggered(
            ChannelHandlerContext ctx, Object objEvent) {
        if (null == ctx ||
                null == ctx.channel() ||
                !(objEvent instanceof HandshakeComplete)) {
            return;
        }

        HandshakeComplete handshakeComplete = (HandshakeComplete) objEvent;

        final HttpHeaders h = handshakeComplete.requestHeaders();

        final List<Map.Entry<String, String>>
                entryList = h.entries();

        final Channel ch = ctx.channel();

        for (Map.Entry<String, String> entry : entryList) {
            if (null == entry ||
                    null == entry.getKey()) {
                continue;
            }

            ch.attr(AttributeKey.valueOf(HTTP_HEADER_PREFIX + entry.getKey()))
                    .set(entry.getValue());
        }
    }

    /**
     * 获取 X-Real-IP 值
     *
     * @param ctx 信道处理器上下文
     * @return X-Real-IP 值
     */
    public static String getXRealIp(ChannelHandlerContext ctx) {
        if (null == ctx ||
                null == ctx.channel()) {
            return null;
        }

        final Channel ch = ctx.channel();
        final AttributeKey<String> attrKey = AttributeKey.valueOf(HTTP_HEADER_PREFIX + "X-Real-IP");

        if (ch.hasAttr(attrKey)) {
            return ch.attr(attrKey).get();
        } else {
            return null;
        }
    }

    public void setConnAlreadyTransfer(boolean connAlreadyTransfer) {
        this.connAlreadyTransfer = connAlreadyTransfer;
    }
}
