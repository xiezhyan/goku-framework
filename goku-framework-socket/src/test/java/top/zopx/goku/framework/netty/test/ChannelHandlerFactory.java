package top.zopx.goku.framework.netty.test;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import top.zopx.goku.framework.netty.bind.factory.BaseChannelHandlerFactory;
import top.zopx.goku.framework.netty.bind.factory.BaseDefaultChannelHandler;
import top.zopx.goku.framework.netty.test.codec.ClientMsgDecoder;
import top.zopx.goku.framework.netty.test.codec.ClientMsgEncoder;
import top.zopx.goku.framework.netty.test.entity.InnerMsg;
import top.zopx.goku.framework.netty.test.execute.MainThreadProcessorSingleton;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/7/29
 */
public class ChannelHandlerFactory implements BaseChannelHandlerFactory {

    @Override
    public ChannelHandler createWebsocketMsgHandler() {
        return new BaseDefaultChannelHandler() {
            @Override
            protected ChannelHandler[] getChannelHandlerArray() {
                return new ChannelHandler[]{
                        new ClientMsgDecoder(),
                        new ClientMsgEncoder()
                };
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                InnerMsg realMsg = (InnerMsg) msg;
                GeneratedMessageV3 protoMsg = realMsg.getProtoMsg();

                MainThreadProcessorSingleton.getInstance().process(
                        ctx,
                        realMsg.getRemoteSessionId(),
                        realMsg.getFromUserId(),
                        protoMsg
                );
            }
        };
    }
}
