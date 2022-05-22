package top.zopx.goku.framework.socket.constant;

import io.netty.util.AttributeKey;

/**
 * @author 俗世游子
 * @date 2021/10/9
 * @email xiezhyan@126.com
 */
public interface AttributeKeyConstant {

    /**
     * 登录之后绑定
     */
    AttributeKey<Long> USER_ATTR = AttributeKey.valueOf("USER_LOGIN_BIND");

    /**
     * 终端版本
     */
    AttributeKey<Integer> PLATFORM = AttributeKey.valueOf("PLAT_FORM");

    /**
     * 应用ID
     */
    AttributeKey<String> DEVICE_ID = AttributeKey.valueOf("DEVICE_ID");

    /**
     * 远程sessionID
     */
    AttributeKey<Integer> SESSION_ID = AttributeKey.valueOf("SESSION_ID");
}
