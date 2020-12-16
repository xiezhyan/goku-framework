package top.zopx.starter.tools.exceptions;


import top.zopx.starter.tools.constants.BusCode;

/**
 * 定义基本错误信息
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public enum BusExceptionEnum {

    DEFAULT_ERROR("ERROR", BusCode.RESULT_ERROR),
    DEFAULT_SUCCESS("OK", BusCode.RESULT_OK),
    PARAM_IS_INVALID("参数无效", BusCode.PARAM_IS_INVALID),
    PARAM_IS_BLANK("参数为空", BusCode.PARAM_IS_BLANK),
    PARAM_TYPE_BIND_ERROR("参数类型错误", BusCode.PARAM_TYPE_BIND_ERROR),
    PARAM_NOT_COMPLETE("参数缺失", BusCode.PARAM_NOT_COMPLETE),
    PARAM_VALIDATE_ERROR("参数校验失败", BusCode.PARAM_VALIDATE_ERROR),
    PARAM_NOT_READABLE("参数反序列化失败", BusCode.PARAM_NOT_READABLE),

    USER_NOT_LOGIN("用户未登录，请先登录", BusCode.USER_NOT_LOGIN),
    USER_LOGIN_ERROR("登录错误", BusCode.USER_LOGIN_ERROR),
    USER_ACCOUNT_FORBIDDEN("账号不可用", BusCode.USER_ACCOUNT_FORBIDDEN),
    USER_NOT_EXIST("用户不存在", BusCode.USER_NOT_EXIST),
    USER_HAS_EXISTED("用户已存在", BusCode.USER_HAS_EXISTED),
    USER_NOT_ACCESS("用户拒绝访问", BusCode.USER_NOT_ACCESS),
    ;
    private String msg;
    private Integer code;

    BusExceptionEnum(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

}
