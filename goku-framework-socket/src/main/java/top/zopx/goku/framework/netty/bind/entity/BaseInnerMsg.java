package top.zopx.goku.framework.netty.bind.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 内部类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/26
 */
public abstract class BaseInnerMsg {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInnerMsg.class);
    /**
     * 来源网关ID
     */
    private int gatewayId;

    /**
     * 远程会话ID，客户端连接网关服务器的标识 Id
     */
    private int remoteSessionId;

    /**
     * 来自用户
     */
    private int fromUserId;
    /**
     * 消息编码
     */
    private int msgCode;

    /**
     * 消息体
     */
    private byte[] data;

    public int getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(int gatewayId) {
        this.gatewayId = gatewayId;
    }

    public int getRemoteSessionId() {
        return remoteSessionId;
    }

    public void setRemoteSessionId(int remoteSessionId) {
        this.remoteSessionId = remoteSessionId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * 释放资源
     */
    public void free() {
        data = null;
    }
}
