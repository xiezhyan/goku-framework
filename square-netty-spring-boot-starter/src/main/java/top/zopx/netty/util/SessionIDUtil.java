package top.zopx.netty.util;

import io.netty.channel.Channel;
import top.zopx.netty.configurator.constant.AttributeKeyConstant;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 对Channel设置sessionID
 *
 * @author 俗世游子
 * @date 2022/2/3
 * @email xiezhyan@126.com
 */
public enum SessionIDUtil {

    INSTANCE,
    ;

    private static final AtomicLong ATOMIC_LONG = new AtomicLong(1);

    /**
     * 设置sessionID
     *
     * @param channel 通道
     */
    public void setSessionID(Channel channel) {
        if (null != channel) {
            channel.attr(AttributeKeyConstant.SESSION_ID).setIfAbsent(ATOMIC_LONG.incrementAndGet());
        }
    }

    /**
     * 得到sessionID
     *
     * @param channel 通道
     * @return sessionID
     */
    public long getSessionID(Channel channel) {
        if (null == channel) {
            return -1;
        }

        return channel.attr(AttributeKeyConstant.SESSION_ID).get();
    }
}
