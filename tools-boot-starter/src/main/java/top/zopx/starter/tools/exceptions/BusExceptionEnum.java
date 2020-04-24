package top.zopx.starter.tools.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import top.zopx.starter.tools.constants.BusCode;

/**
 * version: 定义基本错误信息
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum BusExceptionEnum {
    TOKEN("用户Token不存在或以过期", BusCode.TOKEN_CODE),
    IP("访问被限制-黑名单", BusCode.IP_CODE),
    PARAMS("默认参数出现问题", BusCode.PARAM_CODE),
    AUTH("权限管理异常", BusCode.AUTH_CODE),
    VALIDATE("字段验证未通过", BusCode.VALIDATION_CODE),
    DEFAULT("提交出现异常", BusCode.RESULT_ERROR),
    COPY("Bean出现异常", BusCode.COPY_CODE),
    LIMIT("访问被限制-限流", BusCode.LIMIT_CODE),
    ;

    private String msg;
    private Integer code;
}
