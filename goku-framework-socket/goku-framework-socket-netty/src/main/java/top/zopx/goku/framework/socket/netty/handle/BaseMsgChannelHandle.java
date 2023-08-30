package top.zopx.goku.framework.socket.netty.handle;

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
public abstract class BaseMsgChannelHandle extends ChannelDuplexHandler {

    /**
     * 输出对应的 ChannelHandler信息
     * example:
     *  ChannelHandler[] hArray = {
     *     new InternalMsgDecoder(),
     *     new InternalMsgEncoder(),
     * };
     *
     * @return ChannelHandler[]
     */
    protected abstract ChannelHandler[] getChannelHandlerArray();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (null == ctx) {
            return;
        }

        ChannelPipeline pl = ctx.pipeline();

        for (ChannelHandler h : getChannelHandlerArray()) {
            // 获取处理器类
            Class<? extends ChannelHandler>
                    hClazz = h.getClass();

            if (null == pl.get(hClazz)) {
                pl.addBefore(ctx.name(), hClazz.getSimpleName(), h);
            }
        }
    }
}
