package top.zopx.square.netty.msgGenRecognizer;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import io.netty.util.collection.IntObjectHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 消息code和消息体之间的关系
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/04 22:35
 */
public final class ProtoCodeToProtoRecognizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandleGenMsgRecognizer.class);

    private ProtoCodeToProtoRecognizer() {
    }

    private static class Holder {
        public static final ProtoCodeToProtoRecognizer INSTANCE = new ProtoCodeToProtoRecognizer();
    }

    public static ProtoCodeToProtoRecognizer getInstance() {
        return ProtoCodeToProtoRecognizer.Holder.INSTANCE;
    }

    /**
     * 定义编号，类关联
     */
    private static final Map<Integer, GeneratedMessageV3> CODE_CLASS_MAP = new IntObjectHashMap<>();

    public void init(IClassData classData, ICodeData codeData) {
        if(null == classData || null == codeData) {
            LOGGER.error("数据不全");
            return;
        }

        classData.loadClassData().forEach(clazz -> {
            // 小写
            String clazzName = clazz.getSimpleName().toLowerCase(Locale.ROOT);

            // 遍历枚举对象
            for (CodeMsg msg : codeData.loadCodeData()) {
                String msgCodeName = msg.getName().replace("_", "").toLowerCase(Locale.ROOT);

                if (!msgCodeName.startsWith(clazzName)) {
                    continue;
                }

                try {
                    Object returnObj = clazz.getDeclaredMethod("getDefaultInstance").invoke(clazz);
                    LOGGER.info("{} <=======> {}", msg.getCode(), clazz.getName());

                    CODE_CLASS_MAP.put(msg.getCode(), (GeneratedMessageV3) returnObj);
                } catch (Exception e) {
                    LOGGER.error("反射异常：{}", e.getMessage());
                }
            }
        });
    }

    /**
     * 通过messageV3 得到对应的编码
     *  int msgCode = getMsgCodeByClazz(msg.getClass());
     *
     * @param clazz 消息对象
     * @return 消息编码
     */
    public int getMsgCodeByClazz(Class<? extends GeneratedMessageV3> clazz) {
        if (null == clazz) {
            return -1;
        }

        final Optional<Map.Entry<Integer, GeneratedMessageV3>> optional =
                CODE_CLASS_MAP.entrySet()
                        .stream()
                        .filter(entry -> Objects.equals(entry.getValue().getClass(), clazz))
                        .findFirst();

        if(!optional.isPresent()) {
            LOGGER.error("消息编码获取异常");
            return -1;
        }

        return optional.get().getKey();
    }

    /**
     * 通过消息编码获取消息体
     *  Message.Builder msgBuilder = getClazzByMsgCode(msgCode);
     *  byte[] array;
     *  if (in.hasArray()) {
     *      //堆缓冲
     *      ByteBuf slice = in.slice();
     *      array = slice.array();
     *  } else {
     *      // 直接缓冲
     *      array = new byte[len];
     *      in.readBytes(array, 0, len);
     *  }
     *
     *  // 构建消息对象
     *  msgBuilder.clear();
     *  msgBuilder.mergeFrom(array);
     *
     *  Message message = msgBuilder.build();
     *  if (null != message) {
     *      out.add(message);
     *  }
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

    /**
     * Arrays.stream(KsiImMsgCode.class.getDeclaredClasses())
     *  .filter(GeneratedMessageV3.class::isAssignableFrom)
     *  .collect(Collectors.toList());
     */
    public interface IClassData {
        List<Class<?>> loadClassData();
    }

    public interface ICodeData {
        List<CodeMsg> loadCodeData();
    }

    public static class CodeMsg {
        /**
         * 类名
         */
        private String name;

        /**
         * 编码
         */
        private Integer code;

        public CodeMsg(String name, Integer code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }
}
