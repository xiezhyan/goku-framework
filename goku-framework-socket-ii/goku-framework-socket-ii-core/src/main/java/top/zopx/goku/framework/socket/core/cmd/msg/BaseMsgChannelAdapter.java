package top.zopx.goku.framework.socket.core.cmd.msg;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

/**
 * 基础处理器
 *
 * @author Mr.Xie
 * @date 2022/1/19
 * @email xiezhyan@126.com
 */
public abstract class BaseMsgChannelAdapter extends ChannelDuplexHandler {

    public void handlerAdded(ChannelPipeline pipeline) {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (null == ctx) {
            return;
        }

        handlerAdded(ctx.pipeline());
    }
}
