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
    String REGISTER_SERVER = "redis:publish:register_server";
    /**
     * 断线重连通知
     */
    String CONNECTION_TRANSFER_NOTICE = "redis:publish:connection_transfer_notice";
    /**
     * 用户主动断开连接
     */
    String USER_LOGOUT_NOTICE = "redis:publish:user_logout_notice";
    ;

}
