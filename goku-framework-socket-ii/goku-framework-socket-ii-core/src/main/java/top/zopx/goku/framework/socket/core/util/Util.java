package top.zopx.goku.framework.socket.core.util;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/08/19
 */
public final class Util {

    private Util() {}

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux");
    }

    public static <T> void set(Channel channel, AttributeKey<T> attr, T id) {
        if (Objects.isNull(channel) || Objects.isNull(attr) || Objects.isNull(id)) {
            return;
        }

        channel.attr(attr).setIfAbsent(id);
    }

    public static <T> T get(Channel channel, AttributeKey<T> attr) {
        if (Objects.isNull(channel) || Objects.isNull(attr)) {
            return null;
        }

        return channel.attr(attr).get();
    }

    public static void write(Channel channel, GeneratedMessageV3 msg) {
        if (Objects.isNull(channel) || Objects.isNull(msg)) {
            return;
        }

        channel.writeAndFlush(msg);
    }

}
