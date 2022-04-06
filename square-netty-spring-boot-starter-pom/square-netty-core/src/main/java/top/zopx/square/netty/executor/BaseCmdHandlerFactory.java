package top.zopx.square.netty.executor;

import com.google.protobuf.GeneratedMessageV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.square.netty.handle.ICmdHandler;
import top.zopx.square.netty.msgGenRecognizer.HandleGenMsgRecognizer;

/**
 * @author 俗世游子
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public abstract class BaseCmdHandlerFactory {

    static private final Logger LOGGER = LoggerFactory.getLogger(BaseCmdHandlerFactory.class);
    /**
     * 初始化是否完成
     */
     private volatile boolean initOK = false;

    /**
     * 扫描包
     */
    abstract String getScanPackage();

    /**
     * ICmdHandler和消息体之间的映射初始化操作
     */
    public void tryInit() {
        HandleGenMsgRecognizer.getInstance().getClassHandleMap(getScanPackage());
    }

    /**
     * 通过消息类得到相对应的消息处理器
     *
     * @param cmdClazz 消息类
     * @return 消息处理器
     */
    public ICmdHandler<? extends GeneratedMessageV3> create(Class<?> cmdClazz) {
        if (null == cmdClazz) {
            return null;
        }

        if (!initOK) {
            synchronized (this) {
                if (!initOK) {
                    tryInit();
                    initOK = true;
                }
            }
        }

        if(!initOK) {
            LOGGER.error("初始化异常");
            return null;
        }

        return HandleGenMsgRecognizer.getInstance().get(cmdClazz);
    }
}
