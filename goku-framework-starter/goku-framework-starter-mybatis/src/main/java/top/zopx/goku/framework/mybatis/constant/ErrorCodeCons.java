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
    TOKEN_NOT_ERROR("未传入Token信息", 1000),
    /**
     * Token异常
     */
    TOKEN_EXISTS("Token异常",  1001),
    /**
     * 权限校验异常
     */
    TOKEN_NOT_AUTH("权限校验异常",  1002),
    /**
     * 主键查询结果不存在
     */
    NOT_ENTITY("主键查询结果不存在", 1101),
    /**
     * 修改数据失败
     */
    ERROR_UPDATE("修改数据发生异常", 1101),
    /**
     *
     */
    ERROR_CREATE("创建数据发生异常", 1102),
    ;

    /**
     * 消息提示
     */
    private final String msg;

    /**
     * 消息编码
     */
    private final int code;

    ErrorCodeCons(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
