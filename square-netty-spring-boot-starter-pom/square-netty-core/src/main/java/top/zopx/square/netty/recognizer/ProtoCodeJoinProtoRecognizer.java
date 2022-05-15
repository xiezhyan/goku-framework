package top.zopx.square.netty.recognizer;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.Message;
import io.netty.util.collection.IntObjectHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.square.netty.constant.ICos;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息code和消息体之间的关系
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/04 22:35
 */
public final class ProtoCodeJoinProtoRecognizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgJoinHandlerRecognizer.class);

    private ProtoCodeJoinProtoRecognizer() {
    }

    private static class Holder {
        public static final ProtoCodeJoinProtoRecognizer INSTANCE = new ProtoCodeJoinProtoRecognizer();
    }

    public static ProtoCodeJoinProtoRecognizer getInstance() {
        return ProtoCodeJoinProtoRecognizer.Holder.INSTANCE;
    }

    /**
     * 定义编号，类关联
     */
    private static final Map<Integer, GeneratedMessageV3> CODE_CLASS_MAP = new IntObjectHashMap<>();

    private static final Map<Class<?>, Integer> CLASS_CODE_MAP = new ConcurrentHashMap<>();

    /**
     * 消息编号和服务器工作类型字典
     */
    static private final Map<Integer, ICos> MSGCODE_SERVER_TYPE_MAP = new IntObjectHashMap<>();

    public void tryInit(Class<?> protocolClazz, Enum<?>[] enumValArray, ICos serverType) {
        if (null == protocolClazz ||
                null == enumValArray ||
                enumValArray.length <= 0) {
            return;
        }

        final Map<String, Integer> enumNameAndEnumValMap = new HashMap<>();

        for (Enum<?> enumVal : enumValArray) {
            if (!(enumVal instanceof Internal.EnumLite) ||
                    enumVal.name().equals("_Dummy") ||
                    enumVal.name().equals("UNRECOGNIZED")) {
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

                MSGCODE_SERVER_TYPE_MAP.put(
                        msgCode,
                        serverType
                );
            } catch (Exception ex) {
                // 记录错误日志
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * 根据消息编号获取服务器工作类型
     *
     * @param msgCode 指定的消息编号
     * @return 服务器工作类型
     */
    public ICos getServerJobTypeByMsgCode(int msgCode) {
        if(msgCode < 0) {
            LOGGER.error("根据消息编号获取服务器工作类型处理异常，异常code：{}", msgCode);
            return null;
        }
        return MSGCODE_SERVER_TYPE_MAP.get(msgCode);
    }
    /**
     * 通过messageV3 得到对应的编码
     * int msgCode = getMsgCodeByClazz(msg.getClass());
     *
     * @param clazz 消息对象
     * @return 消息编码
     */
    public int getMsgCodeByClazz(Class<? extends GeneratedMessageV3> clazz) {
        if (null == clazz) {
            LOGGER.error("通过messageV3 得到对应的编码处理异常，异常原因：参数为空");
            return -1;
        }

        return CLASS_CODE_MAP.get(clazz);
    }

    /**
     * 通过消息编码获取消息体
     * Message.Builder msgBuilder = getClazzByMsgCode(msgCode);
     * byte[] array;
     * if (in.hasArray()) {
     * //堆缓冲
     * ByteBuf slice = in.slice();
     * array = slice.array();
     * } else {
     * // 直接缓冲
     * array = new byte[len];
     * in.readBytes(array, 0, len);
     * }
     * <p>
     * // 构建消息对象
     * msgBuilder.clear();
     * msgBuilder.mergeFrom(array);
     * <p>
     * Message message = msgBuilder.build();
     * if (null != message) {
     * out.add(message);
     * }
     *
     * @param code 消息编码
     * @return Message.Builder
     */
    public Message.Builder getClazzByMsgCode(int code) {
        final GeneratedMessageV3 messageV3 = CODE_CLASS_MAP.get(code);
        if (null == messageV3) {
            LOGGER.error("消息对象获取异常");
            return null;
        }

        return messageV3.newBuilderForType();
    }
}
