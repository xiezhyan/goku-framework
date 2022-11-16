package top.zopx.goku.framework.biz.ukey;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.tools.digest.md5.Md5Util;
import top.zopx.goku.framework.tools.util.json.JsonUtil;
import top.zopx.goku.framework.util.Out;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:59
 */
public final class UKey {
    private static final Logger LOGGER = LoggerFactory.getLogger(UKey.class);

    private UKey() {
    }

    private static final String KEY = "ukey";

    private static Config config;

    public static void init(Config config) {
        if (null == config) {
            throw new IllegalArgumentException("config or config.map is null");
        }

        UKey.config = config;
    }

    public static String getuKeyStr(long userId, Out<Long> out) {
        // 获取当前时间戳并计算过期时间
        final long ukeyExpireAt = System.currentTimeMillis() + config.getuKeyTTL();

        // 设置输出参数
        Out.putVal(
                out, ukeyExpireAt
        );

        String origStr = MessageFormat.format(
                "userId={0}&ukeyExpireAt={1}&ukeyPassword={2}",
                String.valueOf(userId),
                String.valueOf(ukeyExpireAt),
                config.getuKeyPassword()
        );
        return Md5Util.INSTANCE.digest(
                origStr.getBytes()
        );
    }

    public static boolean vertify(long userId, String uKeyStr, long ukeyExpireAt) {
        if (ukeyExpireAt <= System.currentTimeMillis()) {
            // 如果 Ukey 已经过期
            return false;
        }

        String origStr = MessageFormat.format(
                "userId={0}&ukeyExpireAt={1}&ukeyPassword={2}",
                String.valueOf(userId),
                String.valueOf(ukeyExpireAt),
                config.getuKeyPassword()
        );

        return Md5Util.INSTANCE.digest(
                origStr.getBytes()
        ).equals(uKeyStr);
    }

    @SuppressWarnings("all")
    public static class Config {

        private String uKeyPassword;

        private Long uKeyTTL;

        public static Config fromJsonData(JsonObject jsonObj) {
            if (Objects.isNull(jsonObj) || !jsonObj.has(KEY)) {
                LOGGER.error("没有对应的配置项，无法加载");
                return null;
            }
            JsonObject uKeyObj = jsonObj.getAsJsonObject(KEY);
            return JsonUtil.getInstance().getGson().fromJson(uKeyObj, Config.class);
        }

        public String getuKeyPassword() {
            return uKeyPassword;
        }

        public void setuKeyPassword(String uKeyPassword) {
            this.uKeyPassword = uKeyPassword;
        }

        public Long getuKeyTTL() {
            return uKeyTTL;
        }

        public void setuKeyTTL(Long uKeyTTL) {
            this.uKeyTTL = uKeyTTL;
        }
    }
}
