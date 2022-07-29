package top.zopx.goku.framework.biz.recognizer;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.biz.constant.IKey;

/**
 * 消息处理器
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/26
 */
public abstract class BaseMsgCodeRecognizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMsgCodeRecognizer.class);

    protected BaseMsgCodeRecognizer() {
    }

    private static boolean initOk = false;

    private void tryInit() {
        if (initOk) {
            LOGGER.error("已经完成加载");
            return;
        }
        synchronized (BaseMsgCodeRecognizer.class) {
            if (initOk) {
                LOGGER.error("已经完成加载");
                return;
            }

            LOGGER.info("--> 开始对消息编码，消息体，服务处理类型进行映射 <--");
            init();
            LOGGER.info("--> 消息编码，消息体，服务处理类型处理完成 <--");
            initOk = true;
        }
    }

    /**
     * 尝试初始化
     * CmdHandlerMsgRecognizer.tryInit();
     */
    protected void init() {}

    /**
     * 通过消息类获取消息编码
     *
     * @param clazz 消息类
     * @return 消息编码
     */
    public int getCodeByMsgObj(Class<? extends GeneratedMessageV3> clazz) {
        tryInit();
        return CmdHandlerMsgRecognizer.getMsgCodeByClazz(clazz);
    }

    /**
     * 通过消息编码获取消息体
     *
     * @param msgCode 消息编码
     * @return 消息体
     */
    public Message.Builder getMsgBuilderByMsgCode(int msgCode) {
        tryInit();
        return CmdHandlerMsgRecognizer.getClazzByMsgCode(msgCode);
    }

    /**
     * 通过消息编码获取服务器类型
     *
     * @param msgCode 消息编码
     * @return IKey
     */
    public IKey getServerTypeByMsgCode(int msgCode) {
        tryInit();
        return CmdHandlerMsgRecognizer.getServerJobTypeByMsgCode(msgCode);
    }
}
