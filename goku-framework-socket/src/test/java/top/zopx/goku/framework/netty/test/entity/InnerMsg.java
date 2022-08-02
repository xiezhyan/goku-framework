package top.zopx.goku.framework.netty.test.entity;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.cluster.entity.BaseInnerMsg;
import top.zopx.goku.framework.netty.test.execute.MsgCodeRecognizer;
import top.zopx.goku.framework.tools.entity.wrapper.R;

/**
 * 内部类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/26
 */
public final class InnerMsg extends BaseInnerMsg {
    private static final Logger LOGGER = LoggerFactory.getLogger(InnerMsg.class);
    /**
     * 设置消息
     *
     * @param msg Protobuf消息
     */
    public void putMsg(GeneratedMessageV3 msg) {
        if (null == msg) {
            return;
        }

        setMsgCode(MsgCodeRecognizer.INSTANCE.getCodeByMsgObj(msg.getClass()));
        setData(msg.toByteArray());
    }

    /**
     * 获取当前编码消息体
     *
     * @return 消息体
     */
    public GeneratedMessageV3 getProtoMsg() {
        // 获取消息构建器
        Message.Builder msgBuilder = MsgCodeRecognizer.INSTANCE.getMsgBuilderByMsgCode(getMsgCode());

        if (null == msgBuilder) {
            LOGGER.error(
                    "未找到消息构建器, msgCode = {}",
                    getMsgCode()
            );
            return null;
        }

        try {
            msgBuilder.clear();
            msgBuilder.mergeFrom(getData());
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
            return null;
        }

        Message newMsg = msgBuilder.build();

        if (newMsg instanceof GeneratedMessageV3) {
            // 如果是 Protobuf 消息,
            return (GeneratedMessageV3) newMsg;
        } else {
            return null;
        }
    }

    @Override
    public int addError(R<?> resultX) {
        return 0;
    }
}
