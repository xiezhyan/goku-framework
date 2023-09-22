package top.zopx.goku.example.socket.common.recognizer;

import top.zopx.goku.example.socket.common.constant.ServerTypeEnum;
import top.zopx.goku.example.socket.proto.auth.Auth;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.socket.netty.constant.IKey;
import top.zopx.goku.framework.socket.netty.execute.BaseMsgCodeRecognizer;

/**
 * 消息识别器
 *
 * @author Mr.Xie
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
            tryInit(
                    Common.class,
                    Common.CommonDef.values(),
                    key
            );
        }

        tryInit(
                Auth.class,
                Auth.AuthDef.values(),
                ServerTypeEnum.AUTH
        );
    }
}
