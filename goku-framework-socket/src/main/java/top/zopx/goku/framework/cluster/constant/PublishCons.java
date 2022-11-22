package top.zopx.goku.framework.cluster.constant;

/**
 * 信息上报
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public interface PublishCons {

    /**
     * 新服务注册
     */
    String REGISTER_SERVER = "goku:server:publish:register_server";
    /**
     * 连接转移
     */
    String CONNECTION_TRANSFER_NOTICE = "goku:server:publish:connection_transfer_notice";
    /**
     * 用户主动断开连接
     */
    String USER_LOGOUT_NOTICE = "goku:server:publish:user_logout_notice";

    /**
     * 踢出用户通知
     */
    String KICK_OUT_USER_NOTICE = "goku:server:publish:kick_out_notice";

    /**
     * 重复登录通知
     */
    String DISCONNECT_DUPLICATE_LOGIN = "goku:server:publish:disconnect_duplicate_login";
}
