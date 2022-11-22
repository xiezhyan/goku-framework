package top.zopx.goku.framework.util;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Objects;

/**
 * 通道工具类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/15 19:07
 */
public final class ChannelOpeUtil {

    /**
     * 设置AttributeKey
     *
     * @param channel 通道
     * @param attr    AttributeKey
     * @param id      数据
     * @param <T>     泛型
     */
    public static <T> void set(Channel channel, AttributeKey<T> attr, T id) {
        if (Objects.isNull(channel) || Objects.isNull(attr) || Objects.isNull(id)) {
            return;
        }

        channel.attr(attr).setIfAbsent(id);
    }

    /**
     * 通过channel获取绑定的AttributeKey
     *
     * @param channel 通道
     * @param attr    AttributeKey
     * @param <T>     泛型
     * @return 数据
     */
    public static <T> T get(Channel channel, AttributeKey<T> attr) {
        if (Objects.isNull(channel) || Objects.isNull(attr)) {
            return null;
        }

        return channel.attr(attr).get();
    }

    /**
     * 将消息写出数据
     *
     * @param channel 指定channel
     * @param msg     消息
     */
    public static void write(Channel channel, GeneratedMessageV3 msg) {
        if (Objects.isNull(channel) || Objects.isNull(msg)) {
            return;
        }

        channel.writeAndFlush(msg);
    }

    /**
     * 对外写出数据
     *
     * @param channel 通道
     * @param msg     数据
     */
    public static void writeToLoop(Channel channel, GeneratedMessageV3 msg) {
        if (Objects.isNull(channel) || Objects.isNull(msg)) {
            return;
        }
        channel.eventLoop().execute(() -> channel.writeAndFlush(msg));
    }
}
