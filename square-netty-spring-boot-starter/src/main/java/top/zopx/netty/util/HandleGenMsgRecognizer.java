package top.zopx.netty.util;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.netty.handle.ICmdHandler;
import top.zopx.starter.tools.tools.reflection.PackageUtil;
import top.zopx.starter.tools.tools.strings.StringUtil;
import top.zopx.starter.tools.tools.web.LogUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ICmdHandle和泛型对象进行绑定
 *
 * @author 俗世游子
 * @date 2022/1/19
 * @email xiezhyan@126.com
 */
public final class HandleGenMsgRecognizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandleGenMsgRecognizer.class);

    /**
     * 消息对象和逻辑处理类的映射
     */
    private static final Map<Class<?>, ICmdHandler<? extends GeneratedMessageV3>> CLASS_HANDLE_MAP = new ConcurrentHashMap<>(128);

    private HandleGenMsgRecognizer() {
    }

    private static class Holder {
        public static final HandleGenMsgRecognizer INSTANCE = new HandleGenMsgRecognizer();
    }

    public static HandleGenMsgRecognizer getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 通过包名获取ICmdHandler
     *
     * @param packageName 包名
     * @return Map<Class < ?>, ICmdHandler<? extends GeneratedMessageV3>>
     */
    public Map<Class<?>, ICmdHandler<? extends GeneratedMessageV3>> getClassHandleMap(String packageName) {
        if (StringUtil.isEmpty(packageName)) {
            return CLASS_HANDLE_MAP;
        }

        try {
            final List<Class<?>> fileList = PackageUtil.INSTANCE.getFileList(packageName, ICmdHandler.class, true);
            if (CollectionUtils.isEmpty(fileList)) {
                return CLASS_HANDLE_MAP;
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

                LogUtil.getInstance(getClass()).info("{} <=======> {}", parameterType.getSimpleName(), handler.getSimpleName());
                CLASS_HANDLE_MAP.put(parameterType, (ICmdHandler<? extends GeneratedMessageV3>) handler.newInstance());
            }
            return CLASS_HANDLE_MAP;
        } catch (Exception e) {
            LOGGER.error("异常信息：", e);
            return CLASS_HANDLE_MAP;
        }
    }

    /**
     * 基于ICmdHandler 集合 关联
     *
     * @param handlerList ICmdHandler List
     * @return Map<Class < ?>, ICmdHandler<? extends GeneratedMessageV3>>
     */
    public Map<Class<?>, ICmdHandler<? extends GeneratedMessageV3>> getClassHandleMap(List<ICmdHandler<? extends GeneratedMessageV3>> handlerList) {
        try {
            if (CollectionUtils.isEmpty(handlerList)) {
                return CLASS_HANDLE_MAP;
            }
            for (ICmdHandler<? extends GeneratedMessageV3> handler : handlerList) {
                Class<?> parameterType = null;
                for (Method method : handler.getClass().getDeclaredMethods()) {
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

                LogUtil.getInstance(getClass()).info("{} <=======> {}", parameterType.getSimpleName(), handler.getClass().getName());
                CLASS_HANDLE_MAP.put(parameterType, handler);
            }

            return CLASS_HANDLE_MAP;
        } catch (Exception e) {
            LOGGER.error("异常信息：", e);
            return CLASS_HANDLE_MAP;
        }
    }
}
