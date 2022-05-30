package top.zopx.goku.framework.material.constant;

import top.zopx.goku.framework.tools.exceptions.IBus;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/30
 */
public enum ErrorBus implements IBus {
    ;

    private final String msg;

    private final int code;

    ErrorBus(String msg, int code) {
        this.msg = msg;
        this.code = code;
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
