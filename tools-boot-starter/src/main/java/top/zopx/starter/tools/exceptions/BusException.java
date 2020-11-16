package top.zopx.starter.tools.exceptions;

import top.zopx.starter.tools.constants.BusCode;

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
        this.code = BusCode.RESULT_ERROR;
    }

    public BusException(BusExceptionEnum busExceptionEnum) {
        super();
        this.msg = busExceptionEnum.getMsg();
        this.code = busExceptionEnum.getCode();
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
