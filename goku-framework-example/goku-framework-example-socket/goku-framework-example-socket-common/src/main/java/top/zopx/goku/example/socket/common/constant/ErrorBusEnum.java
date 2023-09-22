package top.zopx.goku.example.socket.common.constant;

import top.zopx.goku.framework.tools.exception.IBus;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:59
 */
public enum ErrorBusEnum implements IBus {

    /**
     * 参数传递异常
     */
    ERROR("参数传递异常", 400),

    /**
     * 登录参数为空
     */
    LOGIN_EMPTY("登录参数为空", 1000),
    /**
     * 未知的登录方式
     */
    LOGIN_TYPE_NOT_FIND("未知的登录方式", 1001),
    /**
     * 登录失败
     */
    LOGIN_ERROR("登录失败", 1002);

    private final String msg;
    private final int code;

    ErrorBusEnum(String msg, int code) {
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
