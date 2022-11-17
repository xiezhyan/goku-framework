package top.zopx.goku.example.socket.common.entity;

import com.google.gson.JsonObject;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.example.socket.common.recognizer.MsgRecognizer;
import top.zopx.goku.framework.cluster.entity.BaseInnerMsg;
import top.zopx.goku.framework.tools.entity.wrapper.R;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.util.json.JsonUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

/**
 * @author 谢先生
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

        if (message instanceof GeneratedMessageV3) {
            // 如果是 Protobuf 消息,
            return (GeneratedMessageV3) message;
        } else {
            return null;
        }
    }
}
