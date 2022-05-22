package top.zopx.goku.framework.socket.handle;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础业务处理类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/9/13
 */
public interface ICmdHandler<T extends GeneratedMessageV3> {

    /**
     * 日志对象
     */
    Logger LOGGER = LoggerFactory.getLogger(ICmdHandler.class);


    /**
     * 处理命令
     *
     * @param ctx 上下文对象
     * @param cmd 消息体
     */
    default void cmd(ChannelHandlerContext ctx, T cmd) {

    }

    /**
     * 处理命令
     *
     * @param ctx        上下文对象
     * @param cmd        消息体
     * @param sessionId  sessionId
     * @param fromUserId 来源用户ID
     */
    default void cmd(ChannelHandlerContext ctx, T cmd, int sessionId, int fromUserId) {
        LOGGER.info("sessionId={}， fromUserId={}", sessionId, fromUserId);
    }
}