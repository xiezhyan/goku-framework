package top.zopx.goku.framework.tools.exception;

import top.zopx.goku.framework.tools.entity.wrapper.R;

/**
 * 自定义异常信息
 *
 * @author Mr.Xie
 * @date 2020/1/26
 */
public class BusException extends RuntimeException {

    private final String msg;
    private final Integer code;

    private final Object[] format;

    public BusException(R.Meta meta, Object... format) {
        this(meta.getMsg(), meta.getCode(), format);
    }

    public BusException(IBus bus, Object... format) {
        this(bus.getMsg(), bus.getCode(), format);
    }

    public BusException(String msg, Integer code, Object... format) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.format = format;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

    public Object[] getFormat() {
        return format;
    }
}
