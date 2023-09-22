package top.zopx.goku.framework.tools.dict;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 0:06
 */
public interface IEnum<T> {
    /**
     * 通过code获取value
     *
     * @param clazz 枚举class
     * @param code  code
     * @return text
     */
    static <T> String getTextByCode(Class<? extends IEnum<T>> clazz, T code) {
        return Stream.of(clazz.getEnumConstants())
                .filter((IEnum<T> e) -> e.getCode().equals(code))
                .map(IEnum::getMsg)
                .findAny().orElse(null);
    }

    /**
     * 通过text获取code
     *
     * @param clazz 枚举class
     * @param text  text
     * @return code
     */
    static <T> T getCodeByText(Class<? extends IEnum<T>> clazz, String text) {
        return Stream.of(clazz.getEnumConstants())
                .filter((IEnum<T> e) -> StringUtils.equals(e.getMsg(), text))
                .map(IEnum::getCode)
                .findAny().orElse(null);
    }

    /**
     * 获取所有字典枚举项, 排除标记了@Deprecated的字段
     *
     * @param clazz 字典枚举类
     * @return List
     */
    @SuppressWarnings("all")
    static <T> List<IEnum<T>> getAll(Class<?> clazz) {
        Map<String, Field> fieldCache = Arrays.stream(clazz.getDeclaredFields())
                .filter(Field::isEnumConstant)
                .collect(Collectors.toMap(Field::getName, Function.identity()));

        IEnum<T>[] allEnum = (IEnum<T>[]) clazz.getEnumConstants();

        return Stream.of(allEnum)
                .filter(e -> !fieldCache.get(((Enum<?>) e).name()).isAnnotationPresent(Deprecated.class))
                .map(DictPool::getDict)
                .collect(Collectors.toList());
    }

    /**
     * 初始化
     *
     * @param code 字典编码
     * @param text 字典文本
     */
    default void init(T code, String text) {
        DictPool.putDict(this, code, text);
    }

    /**
     * 获取编码
     *
     * @return code
     */
    default T getCode() {
        return DictPool.getDict(this).getCode();
    }

    /**
     * 获取文本
     *
     * @return msg
     */
    default String getMsg() {
        return DictPool.getDict(this).getMsg();
    }

    @SuppressWarnings("all")
    class DictPool {

        private DictPool() {
        }

        private static final Map<IEnum, DictEnum> ENUM_MAP = new ConcurrentHashMap<>();
        private static final Map<String, Class<? extends IEnum>> ENUM_NAME_MAP = new ConcurrentHashMap<>();

        static <T> void putDict(IEnum<T> dict, T code, String text) {
            ENUM_NAME_MAP.put(dict.getClass().getName(), dict.getClass());
            ENUM_MAP.put(dict, new DictEnum<>(code, text));
        }

        /**
         * 通过dictClassName获取指定的enum
         *
         * @param dictClassName dictClassName
         * @return List<IEnum < Object>>
         */
        public static List<IEnum<Object>> getDict(String dictClassName) {
            Class<?> clazz = ENUM_NAME_MAP.get(dictClassName);
            return IEnum.getAll(clazz);
        }

        public static <K extends IEnum<T>, T> DictEnum<T> getDict(K dict) {
            return ENUM_MAP.get(dict);
        }
    }
}
