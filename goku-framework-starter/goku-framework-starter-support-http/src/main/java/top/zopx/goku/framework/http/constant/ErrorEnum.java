package top.zopx.goku.framework.http.constant;

import top.zopx.goku.framework.tools.exception.IBus;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/12
 */
public enum ErrorEnum implements IBus {

    /**
     * Token异常
     */
    TOKEN_EXISTS("token.check", 101),
    /**
     * 未登录
     */
    NOT_LOGIN("auth.not.login", 1000),
    /**
     * 未传入Token信息
     */
    TOKEN_NOT_ERROR("token.notfound", 1001),
    /**
     * 权限校验异常
     */
    TOKEN_NOT_AUTH("auth.check", 1002),
    /**
     * 主键查询结果不存在
     */
    NOT_ENTITY("data.get", 1101),
    /**
     * 修改数据失败
     */
    ERROR_UPDATE("data.update", 1101),
    /**
     * 删除数据发生异常
     */
    ERROR_DELETE("data.delete", 1101),
    /**
     * 保存数据发生异常
     */
    ERROR_CREATE("data.save", 1101),
    ;
    /**
     * 消息提示
     */
    private final String msg;

    /**
     * 消息编码
     */
    private final int code;

    ErrorEnum(String msg, int code) {
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
