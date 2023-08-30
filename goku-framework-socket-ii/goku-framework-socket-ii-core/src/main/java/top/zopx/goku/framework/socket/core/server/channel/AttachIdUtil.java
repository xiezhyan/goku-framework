package top.zopx.goku.framework.socket.core.server.channel;

import io.netty.channel.Channel;
import top.zopx.goku.framework.socket.core.constant.AttributeKeyCons;
import top.zopx.goku.framework.socket.core.util.Util;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 20:49
 */
public final class AttachIdUtil {

    private AttachIdUtil() {}

    private static final AtomicInteger ID_GEN = new AtomicInteger(1);

    public static void attachSessionId(Channel ch) {
        if (null != ch) {
            Util.set(ch, AttributeKeyCons.SESSION_ID, ID_GEN.incrementAndGet());
        }
    }

    public static Integer getSessionId(Channel ch) {
        if (null == ch) {
            return -1;
        } else {
            return Util.get(ch, AttributeKeyCons.SESSION_ID);
        }
    }

    public static Long getUserId(Channel ch) {
        if (null == ch) {
            return -1L;
        } else {
            return Optional.ofNullable(Util.get(ch, AttributeKeyCons.USER_ATTR))
                    .orElse(-1L);
        }
    }

    public static void attachUserId(Channel channel, long userId) {
        if (null == channel) {
            return;
        }

        Util.set(channel, AttributeKeyCons.USER_ATTR, userId);
    }
}
