package top.zopx.square.netty.handle;

import com.google.protobuf.GeneratedMessageV3;

/**
 * 基础业务处理类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/9/13
 */
public interface ICusCmdHandler<T extends GeneratedMessageV3> {

    /**
     * 处理命令
     *
     * @param ctx 上下文对象
     * @param cmd 消息体
     */
    void cmd(ICusChannelHandlerContext ctx, T cmd);
}
