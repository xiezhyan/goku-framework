package top.zopx.goku.framework.socket.core.cmd.execute;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.Message;
import io.netty.util.collection.IntObjectHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.core.constant.IKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 消息处理器，用来处理消息、编码、消息内容间的关系
 * <pre>
 * {@code
 *  public final class MsgRecognizer extends BaseMsgCodeRecognizer {
 *
 *     private MsgRecognizer() {
 *     }
 *
 *     public static MsgRecognizer getInstance() {
 *         return Holder.INSTANCE;
 *     }
 *
 *     private static class Holder {
 *         public static final MsgRecognizer INSTANCE = new MsgRecognizer();
 *     }
 *
 *     @Override
 *     protected void init() {
 *         for (IKey key : ServerTypeEnum.values()) {
 *             tryInit(
 *                     Common.class,
 *                     Common.CommonDef.values(),
 *                     key
 *             );
 *         }
 *
 *         tryInit(
 *                 Auth.class,
 *                 Auth.AuthDef.values(),
 *                 ServerTypeEnum.AUTH
 *         );
 *     }
 * }
 * }
 * </pre>
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/04/26
 */
public abstract class BaseMsgCodeRecognizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMsgCodeRecognizer.class);

    private static final AtomicBoolean INIT_OK = new AtomicBoolean(false);
    /**
     * 定义编号，类关联
     */
    private static final Map<Integer, GeneratedMessageV3> CODE_CLASS_MAP = new IntObjectHashMap<>();
    private static final Map<Class<?>, Integer> CLASS_CODE_MAP = new ConcurrentHashMap<>();
    /**
     * 消息编号和服务器工作类型字典
     */
    private static final Map<Integer, IKey> MSGCODE_SERVER_TYPE_MAP = new IntObjectHashMap<>();


    private void tryInit() {
        if (INIT_OK.get()) {
            LOGGER.debug("已经完成加载");
            return;
        }

        if (INIT_OK.compareAndSet(false, true)) {
            LOGGER.info("--> 开始对消息编码，消息体，服务处理类型进行映射 <--");
            doInit();
            LOGGER.info("--> 消息编码，消息体，服务处理类型处理完成 <--");
        }
    }

    /**
     * 尝试初始化
     * for (ServerType key : ServerType.values()) {
     *  tryInit(
     *      Core.class,
     *      Core.CommMsgCodeDef.values(),
     *      key
     *  );
     * }
     */
    protected abstract void doInit();

    protected void tryInit(Class<?> protocolClazz, Enum<?>[] enumValArray) {
        tryInit(protocolClazz, enumValArray, null);
    }

    protected void tryInit(Class<?> protocolClazz, Enum<?>[] enumValArray, IKey serverType) {
        if (null == protocolClazz ||
                null == enumValArray ||
                enumValArray.length <= 0) {
            return;
        }

        final Map<String, Integer> enumNameAndEnumValMap = new HashMap<>();

        for (Enum<?> enumVal : enumValArray) {
            if (!(enumVal instanceof Internal.EnumLite) ||
                    Objects.equals(enumVal.name(), "_ZERO") ||
                    Objects.equals(enumVal.name(), "UNRECOGNIZED")) {
                continue;
            }

            enumNameAndEnumValMap.put(
                    enumVal.name(),
                    ((Internal.EnumLite) enumVal).getNumber()
            );
        }

        // 获取内置类数组
        Class<?>[] innerClazzArray = protocolClazz.getDeclaredClasses();

        for (Class<?> innerClazz : innerClazzArray) {
            if (!GeneratedMessageV3.class.isAssignableFrom(innerClazz)) {
                // 如果不是消息,
                continue;
            }

            // 消息类
            String clazzName = innerClazz.getSimpleName();
            // 获取消息编号
            Integer msgCode = enumNameAndEnumValMap.get("_" + clazzName);

            if (null == msgCode) {
                continue;
            }

            try {
                // 创建消息对象
                Object newMsg = innerClazz.getDeclaredMethod("getDefaultInstance").invoke(innerClazz);

                LOGGER.info(
                        "关联 {} <==> {}, serverJobType = {}",
                        clazzName,
                        msgCode,
                        serverType
                );

                CODE_CLASS_MAP.put(
                        msgCode, (GeneratedMessageV3) newMsg
                );

                CLASS_CODE_MAP.put(
                        innerClazz, msgCode
                );

                if (null != serverType) {
                    MSGCODE_SERVER_TYPE_MAP.put(
                            msgCode,
                            serverType
                    );
                }
            } catch (Exception ex) {
                // 记录错误日志
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * 通过消息类获取消息编码
     *
     * @param clazz 消息类
     * @return 消息编码
     */
    public int getCodeByMsgObj(Class<? extends GeneratedMessageV3> clazz) {
        tryInit();

        if (null == clazz) {
            LOGGER.error("通过messageV3 得到对应的编码处理异常，异常原因：参数为空");
            return -1;
        }

        return CLASS_CODE_MAP.get(clazz);
    }

    /**
     * 通过消息编码获取消息体
     *
     * @param code 消息编码
     * @return 消息体
     */
    public Message.Builder getMsgBuilderByMsgCode(int code) {
        tryInit();

        final GeneratedMessageV3 messageV3 = CODE_CLASS_MAP.get(code);
        if (null == messageV3) {
            LOGGER.error("消息对象获取异常");
            return null;
        }

        return messageV3.newBuilderForType();
    }

    /**
     * 通过消息编码获取服务器类型
     *
     * @param msgCode 消息编码
     * @return IKey
     */
    public IKey getServerTypeByMsgCode(int msgCode) {
        tryInit();

        if (msgCode < 0) {
            LOGGER.error("根据消息编号获取服务器工作类型处理异常，异常code：{}", msgCode);
            return null;
        }
        return MSGCODE_SERVER_TYPE_MAP.get(msgCode);
    }
}
