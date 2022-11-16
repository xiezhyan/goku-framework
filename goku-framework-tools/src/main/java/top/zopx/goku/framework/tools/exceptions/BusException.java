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
    private final String key;


    /**
     * @deprecated 缺少必要条件
     *
     * @param msg 异常信息
     */
    @Deprecated
    public BusException(String msg) {
        this(msg, 400, msg);
    }

    public BusException(IBus bus) {
        this(bus.getMsg(), bus.getCode(), bus.getKey());
    }

    public BusException(String msg, Integer code, String key) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }
}
