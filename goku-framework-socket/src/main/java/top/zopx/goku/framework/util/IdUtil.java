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
            ChannelOpeUtil.set(ch, AttributeKeyCons.SESSION_ID, ID_GEN.incrementAndGet());
        }
    }

    public static Integer getSessionId(Channel ch) {
        if (null == ch) {
            return -1;
        } else {
            return ChannelOpeUtil.get(ch, AttributeKeyCons.SESSION_ID);
        }
    }

    public static Long getUserId(Channel ch) {
        if (null == ch) {
            return -1L;
        } else {
            return Optional.ofNullable(ChannelOpeUtil.get(ch, AttributeKeyCons.USER_ATTR)).orElse(-1L);
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
        ChannelOpeUtil.set(channel, AttributeKeyCons.USER_ATTR, userId);
    }

    public static void putPlatform(ChannelHandlerContext ctx, String platform) {
        if (null != ctx) {
            putPlatform(ctx.channel(), platform);
        }
    }

    private static void putPlatform(Channel channel, String platform) {
        ChannelOpeUtil.set(channel, AttributeKeyCons.PLATFORM, platform);
    }

    public static void putDeviceId(ChannelHandlerContext ctx, String deviceId) {
        if (null != ctx) {
            putDeviceId(ctx.channel(), deviceId);
        }
    }

    private static void putDeviceId(Channel channel, String deviceId) {
        ChannelOpeUtil.set(channel, AttributeKeyCons.DEVICE_ID, deviceId);
    }
}
