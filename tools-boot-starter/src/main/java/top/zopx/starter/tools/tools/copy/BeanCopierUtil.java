package top.zopx.starter.tools.tools.copy;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import top.zopx.starter.tools.tools.strings.StringUtil;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 复制对象
 *
 * @author sanq.Yan
 * @date 2020/12/12
 */
public class BeanCopierUtil {


    private static final ConcurrentHashMap<String, BeanCopier> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>(16);
    private static final ConcurrentHashMap<String, String> CONCURRENT_HASH_MAP_FIELD = new ConcurrentHashMap<>(64);
    private static final ConcurrentHashMap<String, Object> CONCURRENT_HASH_MAP_OBJECT = new ConcurrentHashMap<>(64);

    private boolean useConverter;

    private BeanCopierUtil() {
    }

    private volatile static BeanCopierUtil INSTANCE = null;

    public static BeanCopierUtil getInstance() {
        if (null == INSTANCE) {
            synchronized (BeanCopierUtil.class) {
                if (null == INSTANCE) {
                    INSTANCE = new BeanCopierUtil();
                }
            }
        }
        return INSTANCE;
    }

    private void setConverter(boolean useConverter) {
        this.useConverter = useConverter;
    }

    /**
     * 得到key
     *
     * @param source 源对象
     * @param target 目标对象
     * @return String
     */
    private <S, T> String getKey(Class<S> source, Class<T> target) {
        return source.getName() + "_" + target.getName();
    }

    /**
     * 得到BeanCopier对象
     *
     * @param source 源对象
     * @param target 目标对象
     * @return BeanCopier
     */
    private <S, T> BeanCopier getCopier(Class<S> source, Class<T> target) {

        String key = "";
        // 从缓存中取出当前对象
        BeanCopier copier = CONCURRENT_HASH_MAP.get((key = getKey(source, target)));
        if (null == copier) {
            // 如果为空，就重新创建，然后存入缓存中
            copier = BeanCopier.create(source, target, useConverter);
            CONCURRENT_HASH_MAP.put(key, copier);
        }
        return copier;
    }


    /**
     * 复制对象 定义转换器
     *
     * @param source    源对象
     * @param target    目标对象
     * @param converter 转换器
     */
    public void copy(Object source, Object target, Converter converter) {
        /**
         * 这里为了防止在外部设置忘记该配置，所以在这里提前加上，不影响
         */
        copy(source, target, converter, null);
    }

    /**
     * 复制对象 如果还存在后续操作，可以通过该方式来进行操作
     *
     * @param source    源对象
     * @param target    目标对象
     * @param converter 转换器
     * @param consumer  通用操作
     */
    public <S, T> void copy(S source, T target, Converter converter, BiConsumer<S, T> consumer) {
        this.setConverter(null != converter);

        BeanCopier copier = getCopier(source.getClass(), target.getClass());

        copier.copy(source, target, converter);

        if (null != consumer) {
            consumer.accept(source, target);
        }
    }


    /**
     * 复制对象 空值定义转换
     *
     * @param source   源对象
     * @param target   目标对象
     * @param consumer 通用操作
     */
    public <S, T> void copyPropertiesIgnoreNull(S source, T target, BiConsumer<S, T> consumer) {
        copy(source, target, new DealNullPropertiesConverter(target), consumer);
    }

    /**
     * 复制集合对象
     *
     * @param source       源对象
     * @param target       目标对象
     * @param consumer     通用操作
     * @param isIgnoreNull 是否对空对象进行设置
     * @return List<T>
     */
    public <S, T> List<T> copyList(List<S> source, Supplier<T> target, BiConsumer<S, T> consumer, boolean isIgnoreNull) {
        return CollectionUtils.isNotEmpty(source) ? source.stream().map(item -> {
            T t = target.get();
            if (isIgnoreNull) {
                copyPropertiesIgnoreNull(item, t, null);
            } else {
                copy(item, t, null);
            }

            if (null != consumer) {
                consumer.accept(item, t);
            }
            return t;
        }).collect(Collectors.toList()) : Collections.emptyList();
    }

    /**
     * 空值字段处理方案：
     * 源中没有数据，那么就从目标数据中取数据，
     * 如果目标数据中也没有数据，那么就真的没有数据了
     */
    public class DealNullPropertiesConverter implements Converter {

        private Object target;

        public DealNullPropertiesConverter(Object target) {
            this.target = target;
        }

        @Override
        public Object convert(Object o, Class aClass, Object o1) {
            if (null == o && null != target) {
                return getFieldValue(target, getFields((String) o1));
            }
            return o;
        }
    }

    /**
     * 反射获取当前成员变量的值
     *
     * @param target 目标
     * @param field  变量名称
     * @return 返回值
     */
    private Object getFieldValue(Object target, String field) {

        Object o = CONCURRENT_HASH_MAP_OBJECT.get(field);

        if (null == o) {
            try {
                o = reflectField(target, field);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return o;
    }

    private Object reflectField(Object target, String field) throws Exception {
        Field declaredField = target.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);
        Object o = declaredField.get(target);
        if (null != o) {
            CONCURRENT_HASH_MAP_OBJECT.put(field, o);
        }
        declaredField.setAccessible(false);
        return o;
    }

    /**
     * 将setXX 转成xX
     *
     * @param setMethod setXX方法
     * @return xX
     */
    private String getFields(String setMethod) {
        String field = CONCURRENT_HASH_MAP_FIELD.get(setMethod);

        if (StringUtil.isBlank(field)) {
            field = getField(setMethod);
        }

        return field;
    }

    private String getField(String setMethod) {
        int len;
        char[] newStrs = new char[(len = setMethod.length() - 3)];
        System.arraycopy(setMethod.toCharArray(), 3, newStrs, 0, len);
        // 转小写
        newStrs[0] = Character.toLowerCase(newStrs[0]);
        CONCURRENT_HASH_MAP_FIELD.put(setMethod, String.valueOf(newStrs));
        return CONCURRENT_HASH_MAP_FIELD.get(setMethod);
    }
}
