package top.zopx.goku.example.socket.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.common.entity.ClientInnerMsg;

import java.nio.charset.StandardCharsets;

/**
 * 内部服务器消息编解码器
 */
public final class ClientInnerMsgCodec extends CombinedChannelDuplexHandler<ClientInnerMsgCodec.Decoder, ClientInnerMsgCodec.Encoder> {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientInnerMsgCodec.class);

    /**
     * 类默认构造器
     */
    public ClientInnerMsgCodec() {
        super.init(new Decoder(), new Encoder());
    }

    /**
     * 消息解码器
     */
    static final class Decoder extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msgObj) {
            try {
                if (null == ctx ||
                        !(msgObj instanceof BinaryWebSocketFrame)) {
                    super.channelRead(ctx, msgObj);
                    return;
                }


                BinaryWebSocketFrame inputFrame = (BinaryWebSocketFrame) msgObj;
                ByteBuf byteBuf = inputFrame.content();

                // 读掉消息长度
                byteBuf.readShort();

                // 创建内部服务器消息
                ClientInnerMsg innerMsg = new ClientInnerMsg();

                innerMsg.setGatewayId(byteBuf.readInt());    // gatewayId
                innerMsg.setRemoteSessionId(byteBuf.readInt()); // remoteSessionId
                innerMsg.setClientIp(readStr(byteBuf));         // clientIP
                innerMsg.setFromUserId(byteBuf.readLong());      // fromUserId
                innerMsg.setMsgCode(byteBuf.readShort());         // msgCode

                // 消息内容
                byte[] msgBody = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(msgBody);
                innerMsg.setData(msgBody);

                // 出发消息读取事件
                ctx.fireChannelRead(innerMsg);
            } catch (Exception ex) {
                // 记录错误日志
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * 消息编码器
     */
    static final class Encoder extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msgObj, ChannelPromise promise) {
            try {
                if (!(msgObj instanceof ClientInnerMsg)) {
                    super.write(ctx, msgObj, promise);
                    return;
                }

                // 转型为内部服务器消息

                ClientInnerMsg innerMsg = (ClientInnerMsg) msgObj;

                if (null == innerMsg.getData()) {
                    // 确保消息体不为空
                    innerMsg.setData(new byte[0]);
                }

                // 创建字节缓冲
                ByteBuf byteBuf = ctx.alloc().buffer();

                // 事先写出一个空长度
                byteBuf.writeShort(0);

                // 写出消息头
                byteBuf.writeInt(innerMsg.getGatewayId());
                byteBuf.writeInt(innerMsg.getRemoteSessionId()); // remoteSessionId
                writeStr(byteBuf, innerMsg.getClientIp());       // clientIP
                byteBuf.writeLong(innerMsg.getFromUserId());     // fromUserId
                byteBuf.writeShort(innerMsg.getMsgCode());       // msgCode

                // 写出消息体
                byteBuf.writeBytes(innerMsg.getData());       // msgBody

                // 最后重新写出消息长度
                byteBuf.setShort(0, byteBuf.readableBytes() - 2);

                // 写出消息
                BinaryWebSocketFrame outputFrame = new BinaryWebSocketFrame(byteBuf);
                super.write(ctx, outputFrame, promise);
            } catch (Exception ex) {
                // 记录错误日志
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * 读取字符串
     *
     * @param byteBuf 字节缓冲
     * @return 字符串值
     */
    private static String readStr(ByteBuf byteBuf) {
        if (null == byteBuf) {
            return "";
        } else {
            return byteBuf.readCharSequence(
                    byteBuf.readShort(), StandardCharsets.UTF_8
            ).toString();
        }
    }

    /**
     * 写出字符串
     *
     * @param byteBuf 字节缓冲
     * @param val     字符串值
     */
    static private void writeStr(ByteBuf byteBuf, String val) {
        if (null == byteBuf) {
            return;
        }

        if (null == val ||
                val.isEmpty()) {
            byteBuf.writeShort(0);
            return;
        }

        byte[] byteArray = val.getBytes(StandardCharsets.UTF_8);
        byteBuf.writeShort(byteArray.length);
        byteBuf.writeBytes(byteArray);
    }
}
