package top.zopx.goku.framework.biz.recognizer;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.netty.bind.handler.BaseCmdHandleContext;
import top.zopx.goku.framework.netty.bind.handler.ICmdContextHandler;
import top.zopx.goku.framework.tools.util.reflection.PackageUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 俗世游子
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public abstract class BaseCmdHandlerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseCmdHandlerFactory.class);

    /**
     * 缓存类
     */
    private static final Map<Class<?>, ICmdContextHandler<? extends BaseCmdHandleContext, ? extends GeneratedMessageV3>> CLASS_HANDLE_MAP = new ConcurrentHashMap<>(4 << 4);
    /**
     * 初始化是否完成
     */
    private volatile boolean initok = false;

    /**
     * 扫描包
     *
     * @return 包名
     */
    public abstract String getScanPackage();

    /**
     * ICmdHandler和消息体之间的映射初始化操作
     */
    public void tryInit() {
        recognizerToCmdAndHandlerByPackage(getScanPackage());
    }

    /**
     * 通过消息类得到相对应的消息处理器
     *
     * @param cmdClazz 消息类
     * @return 消息处理器
     */
    public ICmdContextHandler<? extends BaseCmdHandleContext, ? extends GeneratedMessageV3> create(Class<?> cmdClazz) {
        if (null == cmdClazz) {
            return null;
        }

        if (!initok) {
            synchronized (this) {
                if (!initok) {
                    tryInit();
                    initok = true;
                }
            }
        }

        if (!initok) {
            LOGGER.error("初始化异常");
            return null;
        }

        return CLASS_HANDLE_MAP.get(cmdClazz);
    }

    public static void recognizerToCmdAndHandlerByPackage(String packageName) {
        if (StringUtils.isEmpty(packageName)) {
            return;
        }

        try {
            final List<Class<?>> fileList = PackageUtil.INSTANCE.getFileList(packageName, ICmdContextHandler.class, true);
            if (CollectionUtils.isEmpty(fileList)) {
                return;
            }

            for (Class<?> handler : fileList) {
                Class<?> parameterType = null;
                for (Method method : handler.getDeclaredMethods()) {
                    // 如果是cmd的方法
                    if (!"cmd".equals(method.getName())) {
                        continue;
                    }

                    // 如果方法参数是两位并且最后一位的参数是MessageV3或者其的子类才符合条件
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length < 2 ||
                            parameterTypes[1] == GeneratedMessageV3.class ||
                            !GeneratedMessageV3.class.isAssignableFrom(parameterTypes[1])) {
                        continue;
                    }

                    parameterType = parameterTypes[1];
                    break;
                }

                if (null == parameterType) {
                    continue;
                }

                LOGGER.info("{} <=======> {}", parameterType.getSimpleName(), handler.getSimpleName());
                CLASS_HANDLE_MAP.put(parameterType, (ICmdContextHandler<? extends BaseCmdHandleContext, ? extends GeneratedMessageV3>) handler.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            LOGGER.error("异常信息：", e);
        }
    }
}
