package top.zopx.goku.framework.biz.constant;

/**
 * @author 俗世游子
 * @date 2021/10/9
 * @email xiezhyan@126.com
 */
public enum RedisKeyEnum {


    /**
     * 服务信息
     */
    KEY_SERVER_X_PREFIX("goku:server:current_info:server_%s"),

    /**
     * 用户信息
     */
    KEY_USER_INFO("goku:server:user_info:user_%s"),

    /**
     * 检票
     */
    TICKET_X_PREFIX("goku:server:ticket:ticket_%s"),

    /**
     * 网关
     */
    GATEWAY_USER_LIST("goku:server:gateway:gateway_%s")
    ;

    private final String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String format(Object... values) {
        return String.format(key, values);
    }

    public String getKey() {
        return key;
    }
}
