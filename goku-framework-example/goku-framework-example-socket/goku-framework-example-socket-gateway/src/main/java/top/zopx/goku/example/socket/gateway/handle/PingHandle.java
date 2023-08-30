package top.zopx.goku.example.socket.gateway.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import top.zopx.goku.example.socket.gateway.codec.SemiClientMsgFinished;
import top.zopx.goku.example.socket.proto.common.Common;

/**
 * 客户端消息处理器
 *
 * @author Mr.Xie
 */
public class PingHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (null == ctx ||
                !(msg instanceof SemiClientMsgFinished)) {
            // 如果接到的不是客户端半成品消息,
            super.channelRead(ctx, msg);
            return;
        }

        SemiClientMsgFinished semiClientMsg = (SemiClientMsgFinished) msg;

        if (Common.CommonDef._PingRequest_VALUE != semiClientMsg.getMsgCode()) {
            // 接收到的不是Ping指令
            super.channelRead(ctx, msg);
            return;
        }

        Common.PingRequest pingRequest = Common.PingRequest.parseFrom(semiClientMsg.getData());

        // 发送ping结果
        ctx.writeAndFlush(
                Common.PingResponse.newBuilder().setPingId(pingRequest.getPingId()).build()
        );
    }
}
