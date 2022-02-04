package top.zopx.square.netty.util;

import io.netty.channel.Channel;
import top.zopx.square.netty.configurator.constant.AttributeKeyConstant;

import java.util.Locale;
import java.util.UUID;

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

    /**
     * 设置sessionID
     *
     * @param channel 通道
     */
    public void setSessionID(Channel channel) {
        if (null != channel) {
            channel.attr(AttributeKeyConstant.SESSION_ID).setIfAbsent(UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ROOT));
        }
    }

    /**
     * 得到sessionID
     *
     * @param channel 通道
     * @return sessionID
     */
    public String getSessionID(Channel channel) {
        if (null == channel) {
            return "";
        }

        return channel.attr(AttributeKeyConstant.SESSION_ID).get();
    }
}
