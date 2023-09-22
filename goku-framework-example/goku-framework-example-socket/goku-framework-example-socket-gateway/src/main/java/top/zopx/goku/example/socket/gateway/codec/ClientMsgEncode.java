package top.zopx.goku.example.socket.gateway.codec;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.common.entity.ClientInnerMsg;
import top.zopx.goku.example.socket.common.recognizer.MsgRecognizer;

/**
 * 客户端消息处理器
 *
 * @author Mr.Xie
 */
public class ClientMsgEncode extends ChannelOutboundHandlerAdapter {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMsgEncode.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof GeneratedMessageV3) &&
                !(msg instanceof ClientInnerMsg)) {
            super.write(ctx, msg, promise);
            return;
        }

        try {
            // 定义消息编码和消息体
            int msgCode;
            byte[] msgBody;

            if (msg instanceof ClientInnerMsg) {
                // 如果是内部服务器消息
                ClientInnerMsg innerMsg = (ClientInnerMsg) msg;
                msgCode = innerMsg.getMsgCode();
                msgBody = innerMsg.getData();
                // 释放资源
                innerMsg.free();
            } else {
                GeneratedMessageV3 messageV3 = (GeneratedMessageV3) msg;
                // 如果是协议消息
                msgCode = MsgRecognizer.getInstance().getCodeByMsgObj(messageV3.getClass());
                msgBody = messageV3.toByteArray();
            }

            ByteBuf byteBuf = ctx.alloc().buffer();

            // 先写出消息长度, 避免粘包情况!
            // XXX 注意: 2 = sizeof(short)
            byteBuf.writeShort(2 + msgBody.length);
            byteBuf.writeShort(msgCode);
            byteBuf.writeBytes(msgBody);

            // 写出消息
            BinaryWebSocketFrame outputFrame = new BinaryWebSocketFrame(byteBuf);
            ctx.write(outputFrame, promise);
        } catch (Exception e) {
            LOGGER.error("编码出现异常：", e);
        }
    }
}
