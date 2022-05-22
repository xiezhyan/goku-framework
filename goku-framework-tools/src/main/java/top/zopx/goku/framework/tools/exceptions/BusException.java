package top.zopx.goku.framework.tools.exceptions;

/**
 * 自定义异常信息
 *
 * @author 俗世游子
 * @date 2020/1/26
 */
public class BusException extends RuntimeException {

    private final String msg;
    private final Integer code;

    public BusException(String msg) {
        super(msg);
        this.msg = msg;
        this.code = 400;
    }

    public BusException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BusException(IBus bus) {
        this(bus.getMsg(), bus.getCode());
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

}
