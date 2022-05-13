package top.zopx.starter.mybatis.constant;


import top.zopx.starter.tools.exceptions.IBus;

/**
 * 1000 ~ 1999
 * @author 俗世游子
 * @date 2022/05/12
 */
public enum ErrorCodeCons implements IBus {
    /**
     * 主键查询结果不存在
     */
    NOT_ENTITY("主键查询结果不存在", 1000),
    /**
     * 修改数据失败
     */
    ERROR_UPDATE("修改数据发生异常", 1001),
    /**
     *
     */
    ERROR_CREATE("创建数据发生异常", 1002),
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
