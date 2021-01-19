package top.zopx.starter.tools.constants;

/**
 * 错误码
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class BusCode {



    public static final Integer RESULT_OK = 200;
    public static final Integer RESULT_ERROR = 500;

    /**
     * 参数错误 1001 ~ 1999
     */
    public static final Integer PARAM_IS_INVALID = 1001;
    public static final Integer PARAM_IS_BLANK = 1002;
    public static final Integer PARAM_TYPE_BIND_ERROR = 1003;
    public static final Integer PARAM_NOT_COMPLETE = 1004;
    public static final Integer PARAM_VALIDATE_ERROR = 1054;
    public static final Integer PARAM_NOT_READABLE = 1055;

    /**
     * 用户错误 2001 ~ 2999
     */
    public static final Integer USER_NOT_LOGIN = 2001;
    public static final Integer USER_LOGIN_ERROR = 2002;
    public static final Integer USER_ACCOUNT_FORBIDDEN = 2003;
    public static final Integer USER_NOT_EXIST = 2004;
    public static final Integer USER_HAS_EXISTED = 2005;
    public static final Integer USER_NOT_ACCESS = 2006;
}
