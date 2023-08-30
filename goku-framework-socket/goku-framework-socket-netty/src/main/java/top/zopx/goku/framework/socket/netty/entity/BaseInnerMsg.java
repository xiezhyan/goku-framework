package top.zopx.goku.framework.socket.netty.entity;

import com.google.protobuf.GeneratedMessageV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>内部消息转换</p>
 * <p>示例代码，其中需要使用到{@link top.zopx.goku.framework.socket.netty.execute.BaseMsgCodeRecognizer}</p>
 * <pre>
 * {@code
 *  public class ClientInnerMsg extends BaseInnerMsg {
 *
 *     @Override
 *     public void putMsg(GeneratedMessageV3 msg) {
 *         if (null == msg) {
 *             return;
 *         }
 *
 *         setMsgCode(
 *                 MsgRecognizer.getInstance().getCodeByMsgObj(msg.getClass())
 *         );
 *         setData(msg.toByteArray());
 *     }
 *
 *     @Override
 *     public GeneratedMessageV3 getProtoMsg() {
 *         // 获取消息构建起
 *         Message.Builder builder = MsgRecognizer.getInstance().getMsgBuilderByMsgCode(getMsgCode());
 *         if (null == builder) {
 *             LOGGER.error(
 *                     "未找到消息构建器, msgCode = {}",
 *                     getMsgCode()
 *             );
 *             return null;
 *         }
 *
 *         try {
 *             builder.clear();
 *             builder.mergeFrom(getData());
 *         } catch (Exception ex) {
 *             // 记录错误日志
 *             LOGGER.error(ex.getMessage(), ex);
 *             return null;
 *         }
 *
 *         Message message = builder.build();
 *
 *         if (message instanceof GeneratedMessageV3) {
 *             // 如果是 Protobuf 消息,
 *             return (GeneratedMessageV3) message;
 *         } else {
 *             return null;
 *         }
 *     }
 * }
 * }
 * </pre>
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/04/26
 */
public abstract class BaseInnerMsg {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseInnerMsg.class);
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
    private Long fromUserId;

    /**
     * 客户端IP
     */
    private String clientIp;
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

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
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

    /**
     * 设置消息
     *
     * @param msg Protobuf消息
     */
    public abstract void putMsg(GeneratedMessageV3 msg);
    /**
     * 获取当前编码消息体
     *
     * @return 消息体
     */
    public abstract GeneratedMessageV3 getProtoMsg();
}
