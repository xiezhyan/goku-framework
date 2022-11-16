package top.zopx.goku.example.socket.common.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import top.zopx.goku.example.socket.common.entity.ClientInnerMsg;
import top.zopx.goku.framework.util.IdUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:59
 */
public final class ClientChannelGroup {
    private ClientChannelGroup() {
    }

    private static final Map<Long, Integer> USER_ID_AND_SESSION_ID_MAP = new ConcurrentHashMap<>(64);
    private static final Map<Integer, Channel> CHANNEL_SESSION_MAP = new ConcurrentHashMap<>(64);

    public static void add(ChannelHandlerContext ctx) {
        if (null != ctx) {
            add(ctx.channel());
        }
    }

    private static void add(Channel ch) {
        if (null == ch) {
            return;
        }

        int sessionId = IdUtil.getSessionId(ch);
        CHANNEL_SESSION_MAP.put(sessionId, ch);
    }

    public static void remove(ChannelHandlerContext ctx) {
        if (null != ctx){
            remove(ctx.channel());
        }
    }

    private static void remove(Channel ch) {
        if (null == ch) {
            return;
        }

        long userId = IdUtil.getUserId(ch);
        int sessionId = IdUtil.getSessionId(ch);

        if (sessionId == USER_ID_AND_SESSION_ID_MAP.getOrDefault(userId, -1)) {
            USER_ID_AND_SESSION_ID_MAP.remove(userId);
        }

        CHANNEL_SESSION_MAP.values().remove(ch);
        CHANNEL_SESSION_MAP.values().removeIf(
                (diedChannel) -> !diedChannel.isActive()
        );
    }

    public static void writeAndFlushBySessionId(ClientInnerMsg innerMsg, int remoteSessionId) {
        if (null == innerMsg) {
            return;
        }

        // 获取客户端信道
        Channel ch = getChannelBySessionId(remoteSessionId);

        if (null != ch) {
            ch.writeAndFlush(innerMsg);
        }
    }

    private static Channel getChannelBySessionId(int remoteSessionId) {
        return CHANNEL_SESSION_MAP.get(remoteSessionId);
    }

    public static void relative(long userId, int sessionId) {
        USER_ID_AND_SESSION_ID_MAP.put(userId, sessionId);
    }

    public static Channel getChannelByUserId(long userId) {
        Integer sessionId = USER_ID_AND_SESSION_ID_MAP.get(userId);
        if (null == sessionId) {
            return null;
        }

        Channel channel = CHANNEL_SESSION_MAP.get(sessionId);
        if (null == channel) {
            USER_ID_AND_SESSION_ID_MAP.remove(userId);
        }
        return channel;

    }
}
