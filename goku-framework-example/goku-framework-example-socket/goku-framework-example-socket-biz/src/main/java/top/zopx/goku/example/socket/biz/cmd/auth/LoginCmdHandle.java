package top.zopx.goku.example.socket.biz.cmd.auth;

import top.zopx.goku.example.socket.biz.entity.bo.LoginResult;
import top.zopx.goku.example.socket.biz.service.user.UserService;
import top.zopx.goku.example.socket.proto.auth.Auth;
import top.zopx.goku.framework.socket.netty.handle.BaseCmdHandleContext;
import top.zopx.goku.framework.socket.netty.handle.ICmdContextHandle;
import top.zopx.goku.framework.tools.entity.wrapper.R;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class LoginCmdHandle implements ICmdContextHandle<BaseCmdHandleContext, Auth.LoginRequest> {
    @Override
    public void cmd(BaseCmdHandleContext ctx, Auth.LoginRequest cmd) {
        if (null == ctx ||
                null == cmd) {
            return;
        }

        LOGGER.debug("loginType = {}, login = {}", cmd.getLoginType(), cmd.getLogin());



        UserService.getInstance().doLoginAsync(
                cmd.getLoginType(),
                cmd.getLogin(),
                resultX -> buildResultMsgAndSend(
                        ctx, resultX
                )
        );
    }

    private void buildResultMsgAndSend(BaseCmdHandleContext ctx, R<LoginResult> resultX) {
        if (null == ctx ||
                null == resultX) {
            return;
        }

        if (Boolean.FALSE.equals(resultX.getMeta().getSuccess())) {
            // 写出错误消息
            ctx.send(
                    resultX.getMeta().getCode(), resultX.getMeta().getMsg()
            );
            return;
        }

        LoginResult loginResult = resultX.getData();

        ctx.writeAndFlush(
                Auth.LoginResponse.newBuilder()
                        .setUserId(loginResult.getUserId())
                        .setUserName(loginResult.getUserName())
                        .setTicket(loginResult.getTicket())
                        .setUkey(loginResult.getUkey())
                        .setUkeyExpireAt(loginResult.getUkeyExpireAt())
                        .build()
        );
    }
}
