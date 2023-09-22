package top.zopx.goku.example.constant;


import top.zopx.goku.framework.tools.exception.IBus;

/**
 * @author Mr.Xie
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
