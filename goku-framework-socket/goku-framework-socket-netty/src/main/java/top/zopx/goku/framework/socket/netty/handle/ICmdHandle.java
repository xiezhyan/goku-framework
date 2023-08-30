package top.zopx.goku.framework.socket.netty.handle;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.tools.util.reflection.PackageUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * cmd <--> handle
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/24
 */
public interface ICmdHandle {

    Logger LOGGER = LoggerFactory.getLogger(ICmdHandle.class);

    String getHandlePackageScan();

    AtomicBoolean INIT_OK = new AtomicBoolean(false);

    Map<Class<?>, ICmdContextHandle<? extends BaseCmdHandleContext, ? extends GeneratedMessageV3>> CMD_CONTEXT_HANDLE_MAP = new ConcurrentHashMap<>(128);

    default void tryInit() {
        recognizerToCmdAndHandlerByPackage(getHandlePackageScan());
    }

    /**
     * 通过消息类得到相对应的消息处理器
     *
     * @param cmdClazz 消息类
     * @return 消息处理器
     */
    default ICmdContextHandle<? extends BaseCmdHandleContext, ? extends GeneratedMessageV3> create(Class<?> cmdClazz) {
        if (null == cmdClazz) {
            return null;
        }

        if (INIT_OK.compareAndSet(false, true)) {
            tryInit();
        }

        if (!INIT_OK.get()) {
            LOGGER.error("初始化异常");
            return null;
        }

        return CMD_CONTEXT_HANDLE_MAP.get(cmdClazz);
    }

    default void recognizerToCmdAndHandlerByPackage(String packageName) {
        if (StringUtils.isEmpty(packageName)) {
            return;
        }

        try {
            final List<Class<?>> fileList =
                    PackageUtil.INSTANCE.getFileList(packageName, ICmdContextHandle.class, true);
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
                CMD_CONTEXT_HANDLE_MAP.put(parameterType, (ICmdContextHandle<? extends BaseCmdHandleContext, ? extends GeneratedMessageV3>) handler.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            LOGGER.error("异常信息：", e);
        }
    }
}
