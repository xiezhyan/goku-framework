package top.zopx.square.netty.executor;

import com.google.protobuf.GeneratedMessageV3;
import top.zopx.square.netty.handle.ICmdHandler;
import top.zopx.square.netty.handle.ICusCmdHandler;

/**
 * @author 俗世游子
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public interface ICmdHandlerFactory {

    /**
     * ICmdHandler和消息体之间的映射初始化操作
     */
    void tryInit();

    /**
     * 通过消息类得到相对应的消息处理器
     *
     * @param cmdClazz 消息类
     * @return 消息处理器
     */
    ICmdHandler<? extends GeneratedMessageV3> create(Class<?> cmdClazz);

    /**
     * 通过消息类得到相对应的消息处理器
     *
     * @param cmdClazz 消息类
     * @return 消息处理器
     */
    default ICusCmdHandler<? extends GeneratedMessageV3> create_0(Class<?> cmdClazz) {
        return null;
    }
}
