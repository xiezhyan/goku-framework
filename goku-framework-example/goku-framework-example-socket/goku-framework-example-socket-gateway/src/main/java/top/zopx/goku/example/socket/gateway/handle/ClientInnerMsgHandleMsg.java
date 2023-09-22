package top.zopx.goku.example.socket.gateway.handle;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.common.codec.ClientInnerMsgCodec;
import top.zopx.goku.example.socket.common.entity.ClientInnerMsg;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.socket.core.util.Timer;
import top.zopx.goku.framework.socket.netty.handle.BaseMsgChannelHandle;
import top.zopx.goku.framework.socket.netty.util.ChannelUtil;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 客户端消息处理器
 * @author Mr.Xie
 */
public class ClientInnerMsgHandleMsg extends BaseMsgChannelHandle {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientInnerMsgHandleMsg.class);
    /**
     * Ping 间隔时间
     */
    private static final int PING_INTERVAL_TIME = 5000;
    /**
     * Ping 心跳
     */
    private ScheduledFuture<?> pingHeartbeat;

    private static final AtomicInteger ID_PING = new AtomicInteger(0);

    @Override
    protected ChannelHandler[] getChannelHandlerArray() {
        return new ChannelHandler[]{
            new ClientInnerMsgCodec()
        };
    }

    @Override
    public void userEventTriggered(
            ChannelHandlerContext ctx, Object eventObj) {
        if (null == ctx ||
                !(eventObj instanceof WebSocketClientProtocolHandler.ClientHandshakeStateEvent)) {
            return;
        }

        WebSocketClientProtocolHandler.ClientHandshakeStateEvent
                realEvent = (WebSocketClientProtocolHandler.ClientHandshakeStateEvent) eventObj;

        if (WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE != realEvent) {
            return;
        }

        // 执行 Ping 心跳
        pingHeartbeat = Timer.scheduleWithFixedDelay(
                () -> doPing(ctx),
                PING_INTERVAL_TIME, PING_INTERVAL_TIME,
                TimeUnit.MILLISECONDS
        );
    }

    private void doPing(ChannelHandlerContext ctx) {
        if (null == ctx) {
            return;
        }

        final ClientInnerMsg innerMsg = new ClientInnerMsg();

        Common.PingRequest pingRequest = Common.PingRequest.newBuilder()
                .setPingId(ID_PING.incrementAndGet())
                .build();

        innerMsg.setGatewayId(GatewayApp.getServerId());
        innerMsg.setRemoteSessionId(-1);
        innerMsg.setFromUserId(-1L);
        innerMsg.setMsgCode(Common.CommonDef._PingRequest_VALUE);
        innerMsg.setData(pingRequest.toByteArray());

        ctx.writeAndFlush(innerMsg);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (null == ctx) {
            return;
        }

        if (null != pingHeartbeat) {
            LOGGER.debug("停止 Ping");
            pingHeartbeat.cancel(true);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msgObj) {
        if (null == ctx ||
                !(msgObj instanceof ClientInnerMsg innerMsg)) {
            return;
        }

        // 获取内部服务器消息
        // 一般是由 BizServer 应答给当前服务器 ( 也就是 ProxyServer ) 的消息...
        // 如果是由服务器发回来的 Ping 结果,
        // 则直接跳过...
        LOGGER.info(
                "收到内部服务器返回消息, msgCode = {}",
                innerMsg.getMsgCode()
        );

        //
        // GatewayServer 收到服务器内部消息 ( ClientInnerMsg ) 时,
        // 一般做法就是:
        // 将这个消息拆包装, 把实际消息返回给客户端...
        // @see ClientMsgEncode
        //
        // 根据会话 Id 写出消息
        ChannelUtil.writeAndFlushBySessionId(
                innerMsg, // 该消息会经过 ClientMsgEncoder 编码
                innerMsg.getRemoteSessionId()
        );
    }
}
