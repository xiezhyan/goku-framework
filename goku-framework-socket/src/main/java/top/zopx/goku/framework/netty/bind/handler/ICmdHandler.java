package top.zopx.goku.framework.netty.bind.handler;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础业务处理类
 * <pre>
 * {@code
 *  public final class UserLoginRequestHandler implements ICmdHandler<Account.UserLoginRequest> {
 *
 *     private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginRequestHandler.class);
 *
 *     @Override
 *     public void cmd(ChannelHandlerContext ctx, Account.UserLoginRequest cmd, int sessionId, int fromUserId) {
 *         ICmdHandler.super.cmd(ctx, cmd, sessionId, fromUserId);
 *
 *         if (null == cmd) {
 *             return;
 *         }
 *
 *         LOGGER.debug(
 *                 "收到用户登录消息, propertyStr = {}",
 *                 cmd.getPropertyStr()
 *         );
 *
 *         UserLogin.getInstance().doLoginSync(
 *                 cmd.getLoginMethod(),
 *                 cmd.getPropertyStr(),
 *                 (result) -> buildResultMsgAndSend(ctx, sessionId, fromUserId, result)
 *         );
 *     }
 *
 *
 *     static private void buildResultMsgAndSend(
 *             ChannelHandlerContext ctx, int remoteSessionId, int fromUserId, R<UserLoginResult> resultX) {
 *         if (null == ctx ||
 *                 null == resultX) {
 *             return;
 *         }
 *
 *         final InnerMsg newMsg = new InnerMsg();
 *         newMsg.setRemoteSessionId(remoteSessionId);
 *         newMsg.setFromUserId(fromUserId);
 *
 *         if (R.success != newMsg.addError(resultX)) {
 *             ctx.writeAndFlush(newMsg);
 *             return;
 *         }
 *
 *         // 获取最终结果
 *         final UserLoginResult loginResult = resultX.getData();
 *
 *         // 登录成功
 *         Account.UserLoginResponse.Builder b = Account.UserLoginResponse.newBuilder();
 *         b.setUserId(loginResult.getUserId());
 *         b.setUserName(loginResult.getUserName());
 *         b.setTicket(loginResult.getTicket());
 *         b.setUkey(loginResult.getUkey());
 *         b.setUkeyExpireAt(loginResult.getUkeyExpireAt());
 *
 *         newMsg.putMsg(b.build());
 *
 *         ctx.writeAndFlush(newMsg);
 *     }
 * }
 * }
 * </pre>
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