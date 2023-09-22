package top.zopx.goku.framework.socket.netty.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import top.zopx.goku.framework.socket.netty.constant.AttributeKeyConst;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:59
 */
public final class IdUtil {

    private static final AtomicInteger ID_GEN = new AtomicInteger(1);

    private IdUtil() {
    }

    public static void attachSessionId(ChannelHandlerContext ctx) {
        if (null != ctx) {
            attachSessionId(ctx.channel());
        }
    }

    private static void attachSessionId(Channel ch) {
        if (null != ch) {
            Util.set(ch, AttributeKeyConst.SESSION_ID, ID_GEN.incrementAndGet());
        }
    }

    public static Integer getSessionId(Channel ch) {
        if (null == ch) {
            return -1;
        } else {
            return Util.get(ch, AttributeKeyConst.SESSION_ID);
        }
    }

    public static Integer getSessionId(ChannelHandlerContext ctx) {
        if (null == ctx) {
            return -1;
        } else {
            return getSessionId(ctx.channel());
        }
    }

    public static Long getUserId(Channel ch) {
        if (null == ch) {
            return -1L;
        } else {
            return Optional.ofNullable(Util.get(ch, AttributeKeyConst.USER_ATTR)).orElse(-1L);
        }
    }

    public static Long getUserId(ChannelHandlerContext ctx) {
        if (null == ctx) {
            return -1L;
        } else {
            return getUserId(ctx.channel());
        }
    }

    public static void attachUserId(ChannelHandlerContext ctx, long userId) {
        if (null != ctx) {
            attachUserId(ctx.channel(), userId);
        }
    }

    public static void attachUserId(Channel channel, long userId) {
        Util.set(channel, AttributeKeyConst.USER_ATTR, userId);
    }
}
