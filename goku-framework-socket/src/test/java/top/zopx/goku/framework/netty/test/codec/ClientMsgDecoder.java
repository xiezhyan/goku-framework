package top.zopx.goku.framework.netty.test.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.netty.test.entity.InnerMsg;

/**
 * 消息解码器
 * 消息格式：版本int,长度int,消息编码,消息内容
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/26
 */
@SuppressWarnings("all")
public class ClientMsgDecoder extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMsgDecoder.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msgObj) throws Exception {
        try {
            if (null == ctx ||
                    (!(msgObj instanceof BinaryWebSocketFrame) &&
                            !(msgObj instanceof TextWebSocketFrame))
            ) {
                super.channelRead(ctx, msgObj);
                return;
            }

            ByteBuf byteBuf = null;
            WebSocketFrame socketFrame = null;
            if (msgObj instanceof BinaryWebSocketFrame) {
                socketFrame = (WebSocketFrame) msgObj;
            } else if (msgObj instanceof TextWebSocketFrame) {
                socketFrame = (WebSocketFrame) msgObj;
            }
            byteBuf = socketFrame.content();

            // 版本int,长度int,消息编码,消息内容
            int version = byteBuf.readShort();
            // 长度int,
            int length = byteBuf.readShort();
            // 消息编码,
            int msgCode = byteBuf.readShort();
            // 消息体
            byte[] msgBody = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(msgBody);

            InnerMsg clientMsg = new InnerMsg();
            clientMsg.setMsgCode(msgCode);
            clientMsg.setData(msgBody);

            // 继续派发消息
            ctx.fireChannelRead(clientMsg);
            // 释放资源
            ReferenceCountUtil.safeRelease(socketFrame);
        } catch (Exception e) {
            LOGGER.error("消息解码器失败", e);
        }
    }
}
