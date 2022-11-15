package top.zopx.goku.example.socket.common.constant;

import top.zopx.goku.framework.biz.constant.IKey;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:59
 */
public enum ServerTypeEnum implements IKey {

    /**
     * 登录
     */
    AUTH(0, "AUTH");

    private final int msgCode;
    private final String msgType;

    ServerTypeEnum(int msgCode, String msgType) {
        this.msgCode = msgCode;
        this.msgType = msgType;
    }

    @Override
    public int getMsgCode() {
        return msgCode;
    }

    @Override
    public String getType() {
        return msgType;
    }
}
