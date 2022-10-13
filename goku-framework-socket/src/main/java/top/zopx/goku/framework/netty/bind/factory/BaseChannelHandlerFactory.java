package top.zopx.goku.framework.netty.bind.factory;

import io.netty.channel.ChannelHandler;

/**
 * <pre>
 *  {@code
 *   ChannelDuplexHandler
 *       handlerAdded()  // 将处理器加入
 *          CombinedChannelDuplexHandler<Decoder, Encoder>   // 解码器，编码器
 *          ChannelInboundHandlerAdapter    // 解码器
 *              ctx.fireChannelRead(innerMsg);  //继续派发消息
 *              ReferenceCountUtil.safeRelease(inputFrame); // 释放资源
 *          ChannelOutboundHandlerAdapter   // 编码器
 *              BinaryWebSocketFrame outputFrame = new BinaryWebSocketFrame(byteBuf);
 *              super.write(ctx, outputFrame, promise);
 *       channelRead()   // 业务逻辑处理
 *          process(ctx, msg)
 *              do(xxx, callback)
 *                  AsyncOperationProcessorSingleton.process(
 *                      bindId,
 *                      IO操作,
 *                      callback.apply()    // 回到同步操作
 *                  )
 *       channelActive()    // 有链接
 *       channelInactive()  // 连接断开
 *  }
 * </pre>
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
