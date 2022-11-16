package top.zopx.goku.example.constant;


import top.zopx.goku.framework.tools.exceptions.IBus;

/**
 * @author 俗世游子
 * @date 2022/05/12
 */
public enum ErrorBusEnum implements IBus {
    ;

    /**
     * 消息提示
     */
    private final String msg;

    /**
     * 消息编码
     */
    private final int code;

    /**
     * 国际化标识
     */
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
