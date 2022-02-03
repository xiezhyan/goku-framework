package top.zopx.netty.handle;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;

/**
 * 基础业务处理类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/9/13
 */
public interface ICmdHandler<T extends GeneratedMessageV3> {

    /**
     * 处理命令
     *
     * @param ctx 上下文对象
     * @param cmd 消息体
     */
    void cmd(ChannelHandlerContext ctx, T cmd);

    /**
     * 处理命令
     *
     * @param ctx             上下文对象
     * @param cmd             消息体
     * @param remoteSessionId 远程ID
     * @param fromUserId      来源用户ID
     */
    default void cmd(ChannelHandlerContext ctx, T cmd, int remoteSessionId, String fromUserId) {
    }
}
