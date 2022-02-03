package top.zopx.square.netty.handle;

import io.netty.channel.ChannelHandler;

/**
 * 使用方式：
 *  class ChannelHandlerFactoryImpl_0 extends BaseChannelHandlerFactory {
 *      @Override
 *      public ChannelHandler createMsgHandler() {
 *          return new InternalMsgHandler();
 *      }
 *  }
 *
 *  // 添加内部处理 方式
 *  class InternalMsgHandler extends ChannelDuplexHandler {
 *      @Override
 *      public void handlerAdded(ChannelHandlerContext ctx) {
 *          if (null == ctx) {
 *              return;
 *          }
 *
 *          ChannelHandler[] hArray = {
 *              new InternalMsgDecoder(),
 *              new InternalMsgEncoder(),
 *          };
 *
 *          // 获取信道管线
 *          ChannelPipeline pl = ctx.pipeline();
 *
 *          for (ChannelHandler h : hArray) {
 *              // 获取处理器类
 *              Class<? extends ChannelHandler>
 *                  hClazz = h.getClass();
 *
 *              if (null == pl.get(hClazz)) {
 *                  pl.addBefore(ctx.name(), hClazz.getSimpleName(), h);
 *              }
 *          }
 *      }
 *  }
 *
 * @author 俗世游子
 * @date 2022/1/19
 * @email xiezhyan@126.com
 */
public abstract class BaseChannelHandlerFactory {
    /**
     * 创建APP消息处理器
     * @return ChannelHandler
     */
    public abstract ChannelHandler createAppMsgHandler();
    /**
     * 创建WS消息处理器
     * @return ChannelHandler
     */
    public abstract ChannelHandler createWSMsgHandler();
}
