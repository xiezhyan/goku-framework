package top.zopx.goku.framework.netty.bind.factory;

import io.netty.channel.ChannelHandler;

/**
 *
 * @author 俗世游子
 * @date 2022/1/19
 * @email xiezhyan@126.com
 */
public interface BaseChannelHandlerFactory {
    /**
     * 创建APP消息处理器
     * @return ChannelHandler
     */
    default ChannelHandler createAppMsgHandler() {
        return null;
    }

    /**
     * 创建WS消息处理器
     * @return ChannelHandler
     */
    default ChannelHandler createWebsocketMsgHandler() {
        return null;
    }
}
