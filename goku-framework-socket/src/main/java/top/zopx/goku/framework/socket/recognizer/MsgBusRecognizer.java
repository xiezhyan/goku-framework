package top.zopx.goku.framework.socket.recognizer;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.handle.ICmdHandler;
import top.zopx.goku.framework.tools.util.reflection.PackageUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ICmdHandle和泛型对象进行绑定
 *
 * @Override
 *     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
 *         // 获取消息类
 *         Class<?> msgClazz = msg.getClass();
 *
 *         LogUtil.getInstance(getClass()).info(
 *                 "收到客户端消息, msgClazz = {}, msg = {}",
 *                 msgClazz.getName(),
 *                 msg
 *         );
 *
 *         // 获取指令处理器
 *         ICmdHandler<? extends GeneratedMessageV3>
 *                 cmdHandler = HandleGenMsgRecognizer.get(msgClazz);
 *
 *         if (null == cmdHandler) {
 *             LogUtil.getInstance(getClass()).error(
 *                     "未找到相对应的指令处理器, msgClazz = {}",
 *                     msgClazz.getName()
 *             );
 *             return;
 *         }
 *
 *         // 处理指令
 *         cmdHandler.cmd(ctx.channel(), cast(msg));
 *     }
 *
 * @author 俗世游子
 * @date 2022/1/19
 * @email xiezhyan@126.com
 */
public final class MsgBusRecognizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgBusRecognizer.class);

    /**
     * 消息对象和逻辑处理类的映射
     */
    private static final Map<Class<?>, ICmdHandler<? extends GeneratedMessageV3>> CLASS_HANDLE_MAP = new ConcurrentHashMap<>(128);

    private MsgBusRecognizer() {
    }

    /**
     * 通过包名获取ICmdHandler
     *
     * @param packageName 包名
     * @return Map<Class < ?>, ICmdHandler<? extends GeneratedMessageV3>>
     */
    public static Map<Class<?>, ICmdHandler<? extends GeneratedMessageV3>> recognizerToCmdAndHandlerByPackage(String packageName) {
        if (StringUtils.isEmpty(packageName)) {
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

                LOGGER.info("{} <=======> {}", parameterType.getSimpleName(), handler.getSimpleName());
                CLASS_HANDLE_MAP.put(parameterType, (ICmdHandler<? extends GeneratedMessageV3>) handler.getDeclaredConstructor().newInstance());
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
    public static Map<Class<?>, ICmdHandler<? extends GeneratedMessageV3>> recognizerToCmdAndHandlerByHandlerList(List<ICmdHandler<? extends GeneratedMessageV3>> handlerList) {
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

                // 已经存在，不需要重复
                if(CLASS_HANDLE_MAP.containsKey(parameterType)) {
                    continue;
                }

                LOGGER.info("{} <=======> {}", parameterType.getSimpleName(), handler.getClass().getName());
                CLASS_HANDLE_MAP.put(parameterType, handler);
            }

            return CLASS_HANDLE_MAP;
        } catch (Exception e) {
            LOGGER.error("异常信息：", e);
            return CLASS_HANDLE_MAP;
        }
    }

    /**
     * 获取处理工具类
     *
     * @param msgClazz 消息对象
     * @return ICmdHandler<? extends GeneratedMessageV3>
     */
    public static ICmdHandler<? extends GeneratedMessageV3> get(Class<?> msgClazz) {
        if (null == msgClazz) {
            return null;
        }

        return CLASS_HANDLE_MAP.get(msgClazz);
    }
}
