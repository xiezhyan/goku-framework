package top.zopx.goku.example.socket.common.entity;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import top.zopx.goku.example.socket.common.recognizer.MsgRecognizer;
import top.zopx.goku.framework.socket.netty.entity.BaseInnerMsg;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:59
 */
public class ClientInnerMsg extends BaseInnerMsg {

    @Override
    public void putMsg(GeneratedMessageV3 msg) {
        if (null == msg) {
            return;
        }

        setMsgCode(
                MsgRecognizer.getInstance().getCodeByMsgObj(msg.getClass())
        );
        setData(msg.toByteArray());
    }

    @Override
    public GeneratedMessageV3 getProtoMsg() {
        // 获取消息构建起
        Message.Builder builder = MsgRecognizer.getInstance().getMsgBuilderByMsgCode(getMsgCode());
        if (null == builder) {
            LOGGER.error(
                    "未找到消息构建器, msgCode = {}",
                    getMsgCode()
            );
            return null;
        }

        try {
            builder.clear();
            builder.mergeFrom(getData());
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
            return null;
        }

        Message message = builder.build();

        if (message instanceof GeneratedMessageV3 messageV3) {
            // 如果是 Protobuf 消息,
            return messageV3;
        } else {
            return null;
        }
    }
}
