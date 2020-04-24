package top.zopx.starter.tools.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zopx.starter.tools.constants.BusCode;

/**
 * version: 错误信息
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
