package top.zopx.goku.framework.socket.core.ukey;

import top.zopx.goku.framework.socket.core.config.properties.BootstrapUKey;
import top.zopx.goku.framework.socket.core.util.Out;
import top.zopx.goku.framework.tools.pass.codec.sm3.SM3Util;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.text.MessageFormat;
import java.time.Duration;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/25 21:51
 */
public class UKey {

    private static BootstrapUKey uKey;

    public static void init(BootstrapUKey uKey) {
        if (null == uKey) {
            throw new BusException("config or config.map is null", IBus.ERROR_CODE);
        }
        UKey.uKey = uKey;
    }

    public static String get(long userId, Out<Long> out) {
        // 获取当前时间戳并计算过期时间
        final long ukeyExpireAt = System.currentTimeMillis() + Duration.ofSeconds(uKey.getTtl()).toMillis();

        // 设置输出参数
        Out.putVal(
                out, ukeyExpireAt
        );

        String origStr = MessageFormat.format(
                "userId={0}&ukeyExpireAt={1}&ukeyPassword={2}",
                String.valueOf(userId),
                String.valueOf(ukeyExpireAt),
                uKey.getPassword()
        );
        return SM3Util.digest(origStr);
    }

    public static boolean check(long userId, String uKeyStr, long ukeyExpireAt) {
        if (ukeyExpireAt <= System.currentTimeMillis()) {
            // 如果 Ukey 已经过期
            return false;
        }

        String origStr = MessageFormat.format(
                "userId={0}&ukeyExpireAt={1}&ukeyPassword={2}",
                String.valueOf(userId),
                String.valueOf(ukeyExpireAt),
                uKey.getPassword()
        );

        return SM3Util.digest(origStr).equals(uKeyStr);
    }
}
