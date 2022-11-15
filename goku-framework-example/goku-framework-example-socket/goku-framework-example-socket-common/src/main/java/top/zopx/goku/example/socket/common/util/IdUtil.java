package top.zopx.goku.example.socket.common.util;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.framework.biz.constant.AttributeKeyCons;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.util.json.JsonUtil;
import top.zopx.goku.framework.util.ChannelUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.IllegalFormatCodePointException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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

    public static int getSessionId(Channel ch) {
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
            return ChannelUtil.get(ch, AttributeKeyCons.USER_ATTR);
        }
    }

    public static long getUserId(ChannelHandlerContext ctx) {
        if (null == ctx) {
            return -1L;
        } else {
            return getUserId(ctx.channel());
        }
    }

    public static int getSessionId(ChannelHandlerContext ctx) {
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
