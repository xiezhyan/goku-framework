package top.zopx.goku.framework.mybatis.constant;


import top.zopx.goku.framework.tools.exceptions.IBus;

/**
 * 1000 ~ 1999
 * @author 俗世游子
 * @date 2022/05/12
 */
public enum ErrorCodeCons implements IBus {
    /**
     * 未传入Token信息
     */
    TOKEN_NOT_ERROR("未传入Token信息", 1001, "auth.token.no_in_header"),
    /**
     * Token异常
     */
    TOKEN_EXISTS("Token异常",  1001, "auth.token.vertify_error"),
    /**
     * 权限校验异常
     */
    TOKEN_NOT_AUTH("权限校验异常",  1002, "user.authority.vertify_error"),
    /**
     * 主键查询结果不存在
     */
    NOT_ENTITY("主键查询结果不存在", 1101, "common.data.not_find"),
    /**
     * 修改数据失败
     */
    ERROR_UPDATE("修改数据发生异常", 1101, "common.data.update_error"),
    /**
     * 删除数据发生异常
     */
    ERROR_DELETE("删除数据发生异常", 1101, "common.data.delete_error"),
    /**
     *保存数据发生异常
     */
    ERROR_CREATE("保存数据发生异常", 1101, "common.data.save_error"),
    ;

    /**
     * 消息提示
     */
    private final String msg;

    /**
     * 消息编码
     */
    private final int code;

    /**
     * 国际化标识
     */
    private final String key;

    ErrorCodeCons(String msg, int code, String key) {
        this.msg = msg;
        this.code = code;
        this.key = key;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getKey() {
        return key;
    }
}
