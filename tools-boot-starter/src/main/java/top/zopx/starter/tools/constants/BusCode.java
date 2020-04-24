package top.zopx.starter.tools.constants;

/**
 * version: 错误码
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class BusCode {

    public static final Integer RESULT_OK = 200;    //成功
    public static final Integer RESULT_ERROR = 1005;    //失败

    public static final Integer TOKEN_CODE = 1001;      // token失效
    public static final Integer IP_CODE = 1002;         // IP被加入黑名单/ 被屏蔽/ 被限流
    public static final Integer PARAM_CODE = 1003;      // 参数异常
    public static final Integer AUTH_CODE = 1004;       // 权限拦截异常
    public static final Integer VALIDATION_CODE = 1006; // 字段校验异常
    public static final Integer COPY_CODE = 1007;       // 通过工具copy bean异常
    public static final Integer LIMIT_CODE = 1009;       // 限流

}
