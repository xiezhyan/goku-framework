package top.zopx.square.netty;

import io.netty.channel.ChannelHandler;
import top.zopx.netty.listener.BaseChannelHandlerFactory;

/**
 * @author 俗世游子
 * @date 2022/1/20
 * @email xiezhyan@126.com
 */
public class ChannelHandlerFactoryImpl_0 extends BaseChannelHandlerFactory {
    @Override
    public ChannelHandler createAppMsgHandler() {
        return null;
    }

    @Override
    public ChannelHandler createWSMsgHandler() {
        return new InternalMsgHandler();
    }
}
