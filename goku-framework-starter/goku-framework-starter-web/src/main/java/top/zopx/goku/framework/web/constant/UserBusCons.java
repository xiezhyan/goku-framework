package top.zopx.goku.framework.web.constant;

import top.zopx.goku.framework.tools.exceptions.IBus;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:12
 */
public enum UserBusCons implements IBus {

    /**
     * 用户未登录返回
     */
    NOT_LOGIN(1000, "用户未登录")
    ;

    private final int code;
    private final String msg;

    UserBusCons(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
