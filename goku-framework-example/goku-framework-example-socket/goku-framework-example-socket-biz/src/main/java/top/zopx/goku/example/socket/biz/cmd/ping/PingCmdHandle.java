package top.zopx.goku.example.socket.biz.cmd.ping;


import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.socket.netty.handle.BaseCmdHandleContext;
import top.zopx.goku.framework.socket.netty.handle.ICmdContextHandle;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class PingCmdHandle implements ICmdContextHandle<BaseCmdHandleContext, Common.PingRequest> {
    @Override
    public void cmd(BaseCmdHandleContext ctx, Common.PingRequest cmd) {
        if (null == ctx ||
                null == cmd) {
            return;
        }

        LOGGER.debug(
                "收到内部服务器消息, gatewayId = {}, remoteSessionId = {}, fromUserId = {},  msgData = {}",
                ctx.getGatewayServerId(),
                ctx.getRemoteSessionId(),
                ctx.getFromUserId(),
                cmd.getPingId()
        );

        ctx.writeAndFlush(
                Common.PingResponse.newBuilder().setPingId(cmd.getPingId()).build()
        );
    }
}
