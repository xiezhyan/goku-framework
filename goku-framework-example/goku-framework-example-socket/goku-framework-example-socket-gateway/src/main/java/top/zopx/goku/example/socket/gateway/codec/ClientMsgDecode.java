package top.zopx.goku.example.socket.gateway.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解码器
 * @author Mr.Xie
 */
public class ClientMsgDecode extends ChannelInboundHandlerAdapter {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMsgDecode.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (null == ctx || !(msg instanceof BinaryWebSocketFrame)) {
            super.channelRead(ctx, msg);
            return;
        }

        try {
            BinaryWebSocketFrame binary = (BinaryWebSocketFrame) msg;
            ByteBuf dataByteBuf = binary.content();

            // 长度
            dataByteBuf.readShort();
            // 消息编码
            int msgCode = dataByteBuf.readShort();

            // 内容
            byte[] msgBody = new byte[dataByteBuf.readableBytes()];
            dataByteBuf.readBytes(msgBody);

            // 创建半成品消息
            SemiClientMsgFinished msgFinished = new SemiClientMsgFinished(msgCode, msgBody);

            // 继续向后传递消息
            ctx.fireChannelRead(msgFinished);
            // 释放资源
            ReferenceCountUtil.safeRelease(binary);
        } catch (Exception e) {
            LOGGER.error("解码器出现错误：{}", e);
        }
    }
}
