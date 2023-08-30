package top.zopx.goku.example.socket.gateway.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.gateway.codec.SemiClientMsgFinished;
import top.zopx.goku.example.socket.proto.auth.Auth;
import top.zopx.goku.framework.socket.netty.util.IdUtil;

/**
 * 用户 Id 验证器
 */
public class UserIdValidatorHandle extends ChannelInboundHandlerAdapter {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserIdValidatorHandle.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msgObj) {
        try {
            if (null == ctx ||
                    !(msgObj instanceof SemiClientMsgFinished)) {
                // 如果接到的不是客户端半成品消息,
                super.channelRead(ctx, msgObj);
                return;
            }

            SemiClientMsgFinished semiClientMsg = (SemiClientMsgFinished) msgObj;

            boolean izSafeMsg;

            switch (semiClientMsg.getMsgCode()) {
                case Auth.AuthDef._LoginRequest_VALUE:
                    izSafeMsg = true;
                    break;
                default:
                    izSafeMsg = false;
            }

            if (izSafeMsg) {
                // 如果是安全消息,
                super.channelRead(ctx, msgObj);
                return;
            }

            if (IdUtil.getUserId(ctx) <= 0) {
                LOGGER.error(
                        "没有设置用户 Id, 不能处理消息!, msgCode = {}",
                        semiClientMsg.getMsgCode()
                );

                // 如果不是安全消息,
                // 并且还没有设置用户 Id,
                ctx.disconnect();
                return;
            }

            super.channelRead(ctx, msgObj);
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
