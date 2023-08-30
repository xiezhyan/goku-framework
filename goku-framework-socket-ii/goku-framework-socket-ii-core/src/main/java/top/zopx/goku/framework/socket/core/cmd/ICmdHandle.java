package top.zopx.goku.framework.socket.core.cmd;

import com.google.protobuf.GeneratedMessageV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.core.cmd.context.BaseCmdHandleContext;

/**
 * 基础业务处理类
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2021/9/13
 */
public interface ICmdHandle<CTX extends BaseCmdHandleContext, T extends GeneratedMessageV3>  {

    /**
     * 日志对象
     */
    Logger LOGGER = LoggerFactory.getLogger(ICmdHandle.class);


    /**
     * 处理命令
     *
     * @param ctx 上下文对象
     * @param cmd 消息体
     */
    default void cmd(CTX ctx, T cmd) {
        LOGGER.debug(
                """
                    gatewayServerId = {},
                    remoteSessionId = {},
                    clientIp = {},
                    fromUserId = {},
                    cmd = {}
                """,
                ctx.getGatewayServerId(),
                ctx.getRemoteSessionId(),
                ctx.getClientIp(),
                ctx.getFromUserId(),
                cmd
        );
    }
}