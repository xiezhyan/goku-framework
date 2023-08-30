package top.zopx.goku.example.socket.biz.handle;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.biz.context.CmdHandleContext;
import top.zopx.goku.example.socket.common.async.MainThreadPoolExecutorSingleton;
import top.zopx.goku.example.socket.common.codec.ClientInnerMsgCodec;
import top.zopx.goku.example.socket.common.entity.ClientInnerMsg;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.socket.netty.handle.BaseMsgChannelHandle;
import top.zopx.goku.framework.socket.netty.handle.BaseCmdHandleContext;
import top.zopx.goku.framework.socket.netty.util.GatewayUtil;

/**
 * 客户端消息处理器
 * @author Mr.Xie
 */
public class BizMsgHandleMsg extends BaseMsgChannelHandle {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BizMsgHandleMsg.class);

    @Override
    protected ChannelHandler[] getChannelHandlerArray() {
        return new ChannelHandler[]{
                new ClientInnerMsgCodec()
        };
    }

    @Override
    public void channelRead(ChannelHandlerContext nettyCtx, Object msgObj) {
        if (null == nettyCtx ||
                !(msgObj instanceof ClientInnerMsg)) {
            return;
        }

        ClientInnerMsg innerMsg = (ClientInnerMsg) msgObj;
        // 获取协议消息
        GeneratedMessageV3 protoMsg = innerMsg.getProtoMsg();

        if (Common.CommonDef._PingRequest_VALUE != innerMsg.getMsgCode()) {
            LOGGER.info(
                    "收到内部服务器消息, proxyServerId = {}, remoteSessionId = {}, fromUserId = {}, msgCode = {}, msgClazz = {}",
                    innerMsg.getGatewayId(),
                    innerMsg.getRemoteSessionId(),
                    innerMsg.getFromUserId(),
                    innerMsg.getMsgCode(),
                    null == protoMsg ? "NULL" : protoMsg.getClass().getSimpleName()
            );
        }

        BaseCmdHandleContext ctx = new CmdHandleContext(nettyCtx.channel())
                .setGatewayServerId(innerMsg.getGatewayId())
                .setRemoteSessionId(innerMsg.getRemoteSessionId())
                .setFromUserId(innerMsg.getFromUserId())
                .setClientIp(innerMsg.getClientIp());

        // 处理命令对象
        MainThreadPoolExecutorSingleton.getInstance().process(
                ctx, protoMsg
        );

        innerMsg.free();
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (null == ctx) {
            return;
        }

        LOGGER.warn("代理服务器已断开, gatewayId = {}", GatewayUtil.getGatewayId(ctx));

        GatewayUtil.remove(ctx);
    }

    @Override
    public void userEventTriggered(
            ChannelHandlerContext ctx, Object objEvent) {
        if (null == ctx ||
                !(objEvent instanceof WebSocketServerProtocolHandler.HandshakeComplete)) {
            return;
        }

        WebSocketServerProtocolHandler.HandshakeComplete
                handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) objEvent;

        // 获取代理服务器 Id
        String strServerId = handshakeComplete.requestHeaders().get("gateway_id");

        if (null == strServerId ||
                strServerId.isEmpty()) {
            return;
        }

        LOGGER.info(
                "代理服务器已接入, gatewayId = {}",
                strServerId
        );

        GatewayUtil.putGatewayId(
                ctx, Integer.parseInt(strServerId)
        );

        // 添加到服务器分组
        GatewayUtil.add(ctx);
    }
}
