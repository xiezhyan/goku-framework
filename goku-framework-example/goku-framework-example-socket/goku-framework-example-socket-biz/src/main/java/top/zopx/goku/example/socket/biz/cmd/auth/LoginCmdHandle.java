package top.zopx.goku.example.socket.biz.cmd.auth;

import top.zopx.goku.example.socket.proto.auth.Auth;
import top.zopx.goku.framework.netty.bind.handler.BaseCmdHandleContext;
import top.zopx.goku.framework.netty.bind.handler.ICmdContextHandler;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class LoginCmdHandle implements ICmdContextHandler<BaseCmdHandleContext, Auth.LoginRequest> {
    @Override
    public void cmd(BaseCmdHandleContext ctx, Auth.LoginRequest cmd) {
        if (null == ctx ||
                null == cmd) {
            return;
        }

        LOGGER.debug("loginType = {}, login = {}", cmd.getLoginType(), cmd.getLogin());
    }
}
