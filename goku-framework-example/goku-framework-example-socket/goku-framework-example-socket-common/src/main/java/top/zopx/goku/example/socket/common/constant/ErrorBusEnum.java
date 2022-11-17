package top.zopx.goku.example.socket.common.constant;

import top.zopx.goku.framework.tools.exceptions.IBus;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:59
 */
public enum ErrorBusEnum implements IBus {

    /**
     * 参数传递异常
     */
    ERROR("参数传递异常", 400, "参数传递异常"),

    /**
     * 登录参数为空
     */
    LOGIN_EMPTY("登录参数为空", 1000, "登录参数为空"),
    /**
     * 未知的登录方式
     */
    LOGIN_TYPE_NOT_FIND("未知的登录方式", 1001, "未知的登录方式"),
    /**
     * 登录失败
     */
    LOGIN_ERROR("登录失败", 1002, "登录失败");

    private final String msg;
    private final int code;
    private final String key;

    ErrorBusEnum(String msg, int code, String key) {
        this.msg = msg;
        this.code = code;
        this.key = key;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getKey() {
        return key;
    }
}
