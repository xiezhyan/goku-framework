package top.zopx.netty.configurator.constant;

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
    AttributeKey<String> USER_ATTR = AttributeKey.valueOf("USER_LOGIN_BIND");

    /**
     * 心跳检测机制
     */
    AttributeKey<Integer> PING_COUNT = AttributeKey.valueOf("PING_COUNT");

    /**
     * 终端版本
     */
    AttributeKey<Integer> PLATFORM = AttributeKey.valueOf("PLAT_FORM");

    /**
     * 应用ID
     */
    AttributeKey<String> DEVICE_ID = AttributeKey.valueOf("DEVICE_ID");
    /**
     * 应用ID
     */
    AttributeKey<Long> SESSION_ID = AttributeKey.valueOf("SESSION_ID");

    /**
     * 最大心跳检测次数
     */
    int MAX_PING_COUNT = 3;

}
