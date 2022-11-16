package top.zopx.goku.framework.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import top.zopx.goku.framework.biz.constant.AttributeKeyCons;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 谢先生
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
            ChannelUtil.set(ch, AttributeKeyCons.SESSION_ID, ID_GEN.incrementAndGet());
        }
    }

    public static Integer getSessionId(Channel ch) {
        if (null == ch) {
            return -1;
        } else {
            return ChannelUtil.get(ch, AttributeKeyCons.SESSION_ID);
        }
    }

    public static Long getUserId(Channel ch) {
        if (null == ch) {
            return -1L;
        } else {
            return Optional.ofNullable(ChannelUtil.get(ch, AttributeKeyCons.USER_ATTR)).orElse(-1L);
        }
    }

    public static Long getUserId(ChannelHandlerContext ctx) {
        if (null == ctx) {
            return -1L;
        } else {
            return getUserId(ctx.channel());
        }
    }

    public static Integer getSessionId(ChannelHandlerContext ctx) {
        if (null == ctx) {
            return -1;
        } else {
            return getSessionId(ctx.channel());
        }
    }

    public static void putUserId(ChannelHandlerContext ctx, long userId) {
        if (null != ctx) {
            putUserId(ctx.channel(), userId);
        }
    }

    private static void putUserId(Channel channel, long userId) {
        ChannelUtil.set(channel, AttributeKeyCons.USER_ATTR, userId);
    }
}
