package top.zopx.goku.framework.redis.bus.constant;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/6/7 21:05
 */
public enum RedisKeyEnum {
    KEY_CODE_GENERIC("cache:bus:code:%s"),
    ;

    private final String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String format(Object... args) {
        return String.format(this.key, args);
    }
}
