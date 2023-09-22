package top.zopx.goku.framework.support.activiti.constant;

import top.zopx.goku.framework.tools.exception.IBus;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/9
 */
public enum ActivitiErrorEnum implements IBus {

    MODEL_NOT_FOUND("model.not.found", 2000),

    MODEL_NOT_DELETE("model.not.delete", 2001)

    ;

    private final String msg;

    private final int code;

    ActivitiErrorEnum(String msg, int code) {
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
