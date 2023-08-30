package top.zopx.goku.example.socket.gateway.codec;

/**
 * 半成品消息：用于在网关内部消息流转
 * @author Mr.Xie
 */
public final class SemiClientMsgFinished  {

    /**
     * 消息编码
     */
    private int msgCode;

    /**
     * 消息内容
     */
    private byte[] data;

    public SemiClientMsgFinished(int msgCode, byte[] data) {
        this.msgCode = msgCode;
        this.data = data;
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

    public void free() {
        data = null;
    }

}
