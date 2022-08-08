package top.zopx.goku.framework.cluster.entity;

import com.google.protobuf.GeneratedMessageV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.tools.entity.wrapper.R;

/**
 * <p>内部消息转换</p>
 * <p>示例代码，其中需要使用到{@link top.zopx.goku.framework.biz.recognizer.BaseMsgCodeRecognizer}</p>
 * <pre>
 * {@code
 *  class InnerMsg extends BaseInnerMsg {
 *      public void putMsg(GeneratedMessageV3 msg) {
 *          if (null == msg) {
 *              return;
 *          }
 *          setMsgCode(MsgCodeRecognizer.INSTANCE.getCodeByMsgObj(msg.getClass()));
 *          setData(msg.toByteArray());
 *      }
 *      public GeneratedMessageV3 getProtoMsg() {
 *          // 获取消息构建器
 *          Message.Builder msgBuilder = MsgCodeRecognizer.INSTANCE.getMsgBuilderByMsgCode(getMsgCode());
 *
 *          if (null == msgBuilder) {
 *              LOGGER.error("未找到消息构建器, msgCode = {}",getMsgCode());
 *              return null;
 *          }
 *
 *          try {
 *              msgBuilder.clear();
 *              msgBuilder.mergeFrom(getData());
 *          } catch (Exception ex) {
 *              // 记录错误日志
 *              LOGGER.error(ex.getMessage(), ex);
 *              return null;
 *          }
 *
 *          Message newMsg = msgBuilder.build();
 *
 *          if (newMsg instanceof GeneratedMessageV3) {
 *              // 如果是 Protobuf 消息,
 *              return (GeneratedMessageV3) newMsg;
 *          } else {
 *              return null;
 *          }
 *      }
 *
 *      @Override
 *      public int addError(R<?> resultX) {
 *          return 0;
 *      }
 *  }
 * }
 * </pre>
 * @author 俗世游子
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

    /**
     * 设置异常返回
     *
     * @param resultX R
     * @return code
     */
    public abstract int addError(R<?> resultX);
}
