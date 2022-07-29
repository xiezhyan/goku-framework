package top.zopx.goku.framework.netty.test.codec;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.netty.test.execute.MsgCodeRecognizer;

/**
 * 消息编码器
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/26
 */
@SuppressWarnings("all")
public class ClientMsgEncoder extends ChannelOutboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMsgEncoder.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msgObj, ChannelPromise promise) throws Exception {
        try {
            if (null == ctx ||
                    (!(msgObj instanceof GeneratedMessageV3))
            ) {
                super.write(ctx, msgObj, promise);
                return;
            }

            // 定义消息编码和消息体
            int msgCode = -1;
            byte[] msgBody = null;

            if (msgObj instanceof GeneratedMessageV3) {
                GeneratedMessageV3 messageV3 = (GeneratedMessageV3) msgObj;
                // 如果是协议消息
                msgCode = MsgCodeRecognizer.INSTANCE.getCodeByMsgObj(messageV3.getClass());
                msgBody = messageV3.toByteArray();
            }

            ByteBuf byteBuf = ctx.alloc().buffer();

            // 版本号
            byteBuf.writeShort(2 + 1);
            // 消息长度 注意: 2 = sizeof(short)
            byteBuf.writeShort(2 + msgBody.length);
            // 消息编码
            byteBuf.writeShort(msgCode);
            // 消息体
            byteBuf.writeBytes(msgBody);

            // 写出消息
            BinaryWebSocketFrame outputFrame = new BinaryWebSocketFrame(byteBuf);
            ctx.write(outputFrame, promise);
        } catch (Exception e) {
            LOGGER.error("消息编码失败", e);
        }
    }
}
