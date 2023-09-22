package top.zopx.goku.framework.socket.netty.constant;

import io.netty.util.AttributeKey;

/**
 * @author Mr.Xie
 * @date 2021/10/9
 * @email xiezhyan@126.com
 */
public interface AttributeKeyConst {

    /**
     * 登录之后绑定
     */
    AttributeKey<Long> USER_ATTR = AttributeKey.valueOf("USER_LOGIN_BIND");

    /**
     * 远程sessionID
     */
    AttributeKey<Integer> SESSION_ID = AttributeKey.valueOf("SESSION_ID");

    /**
     * 网关ID
     */
    AttributeKey<Integer> GATEWAY_SERVER_ID = AttributeKey.valueOf("GATEWAY_SERVER_ID");
}
