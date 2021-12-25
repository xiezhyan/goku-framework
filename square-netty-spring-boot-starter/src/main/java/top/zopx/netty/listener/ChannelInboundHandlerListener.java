package top.zopx.netty.listener;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author 俗世游子
 * @date 2021/10/4
 * @email xiezhyan@126.com
 */
public interface ChannelInboundHandlerListener {

    /**
     * 心跳机制
     * @param ctx
     * @param evt
     */
    default void doIdleStateEvent(ChannelHandlerContext ctx, IdleStateEvent evt) {}

    /**
     * 异常
     * @param ctx
     * @param cause
     */
    default void doException(ChannelHandlerContext ctx, Throwable cause) {}

    /**
     * 链接进入
     * @param ctx
     */
    void doActive(ChannelHandlerContext ctx);
    /**
     * 消息读取
     * @param ctx
     * @param msg
     */
    void doRead(ChannelHandlerContext ctx, Object msg);

    /**
     * 链接断开
     * @param ctx
     */
    void doInactive(ChannelHandlerContext ctx);
}
