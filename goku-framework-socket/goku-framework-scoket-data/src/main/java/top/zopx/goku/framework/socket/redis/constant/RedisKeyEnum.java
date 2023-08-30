package top.zopx.goku.framework.socket.redis.constant;

/**
 * @author Mr.Xie
 * @date 2021/10/9
 * @email xiezhyan@126.com
 */
public enum RedisKeyEnum {


    /**
     * 当前需要注册服务信息，生命周期极短
     */
    KEY_SERVER_X_PREFIX("goku:server:current_info:server_%s"),

    /**
     * 网关： 记录网关存储信息。
     * - 包括当前存在用户信息
     */
    GATEWAY_USER_LIST("goku:server:gateway:gateway_%s"),

    /**
     * 新服务注册
     */
    REGISTER_SERVER("goku:server:publish:register_server"),
    /**
     * 连接转移
     */
    CONNECTION_TRANSFER_NOTICE("goku:server:publish:connection_transfer_notice"),
    /**
     * 用户主动断开连接
     */
    USER_LOGOUT_NOTICE("goku:server:publish:user_logout_notice"),

    /**
     * 踢出用户通知
     */
    KICK_OUT_USER_NOTICE("goku:server:publish:kick_out_notice"),

    /**
     * 重复登录通知
     */
    DISCONNECT_DUPLICATE_LOGIN("goku:server:publish:disconnect_duplicate_login"),
    /**
     * 用户信息
     * - 包括用户基本信息
     * - 用户所在网关信息
     * - 用户session信息
     */
    KEY_USER_INFO("goku:user:user_info:user_%s"),

    /**
     * 检票
     */
    TICKET_X_PREFIX("goku:user:ticket:ticket_%s"),
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
