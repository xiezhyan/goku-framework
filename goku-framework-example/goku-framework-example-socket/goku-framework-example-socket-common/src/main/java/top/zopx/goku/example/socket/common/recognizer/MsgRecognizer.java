package top.zopx.goku.example.socket.common.recognizer;

import com.google.protobuf.GeneratedMessageV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.common.constant.ServerTypeEnum;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.biz.constant.IKey;
import top.zopx.goku.framework.biz.recognizer.BaseMsgCodeRecognizer;
import top.zopx.goku.framework.biz.recognizer.CmdHandlerMsgRecognizer;
import top.zopx.goku.framework.tools.util.reflection.PackageUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息识别器
 *
 * @author 俗世游子
 */
public final class MsgRecognizer extends BaseMsgCodeRecognizer {
    /**
     * 私有化类默认构造器
     */
    private MsgRecognizer() {
    }

    public static MsgRecognizer getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final MsgRecognizer INSTANCE = new MsgRecognizer();
    }

    @Override
    protected void init() {
        for (IKey key : ServerTypeEnum.values()) {
            CmdHandlerMsgRecognizer.tryInit(
                    Common.class,
                    Common.CommonDef.values(),
                    key
            );
        }
    }
}
