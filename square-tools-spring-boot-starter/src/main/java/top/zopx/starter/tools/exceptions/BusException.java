package top.zopx.starter.tools.exceptions;

import org.springframework.http.HttpStatus;

/**
 * 自定义异常信息
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class BusException extends RuntimeException {

    private String msg;
    private Integer code;

    public BusException(String msg) {
        super();
        this.msg = msg;
        this.code = HttpStatus.BAD_REQUEST.value();
    }

    public BusException(String msg, Integer code) {
        super();
        this.msg = msg;
        this.code = code;
    }

    public BusException(IBus bus) {
        super();
        this.msg = bus.getMsg();
        this.code = bus.getCode();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
