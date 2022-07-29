package top.zopx.goku.framework.netty.test.execute;

import top.zopx.goku.framework.biz.recognizer.BaseMsgCodeRecognizer;

/**
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/7/29
 */
public class MsgCodeRecognizer extends BaseMsgCodeRecognizer {

    public static final MsgCodeRecognizer INSTANCE = new MsgCodeRecognizer();
    @Override
    protected void init() {
        System.out.println("开始对ProtoMsg，Code进行映射");
    }
}
