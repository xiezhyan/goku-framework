package top.zopx.starter.tools.tools.copy;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 复制对象
 *
 * @author 俗世游子
 * @date 2020/12/12
 */
public class BeanCopierUtil {

    private static final WeakHashMap<String, BeanCopier> COPY_MAP = new WeakHashMap<>(16);

    private boolean useConverter;

    private BeanCopierUtil() {
    }

    private static class Holder {
        public static final BeanCopierUtil INSTANCE = new BeanCopierUtil();
    }

    public static BeanCopierUtil getInstance() {
        return Holder.INSTANCE;
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
        String key = getKey(source, target);
        // 如果不存在，就返回默认Copier
        BeanCopier beanCopier = COPY_MAP.getOrDefault(key, BeanCopier.create(source, target, useConverter));
        // 如果Map中不存在，就放置到Map中
        if (!COPY_MAP.containsKey(key)) {
            COPY_MAP.put(key, beanCopier);
        }
        return beanCopier;
    }


    /**
     * 复制对象 定义转换器
     *
     * @param source    源对象
     * @param target    目标对象
     * @param converter 转换器
     */
    public <S, T> void copy(S source, T target, Converter converter) {
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
    public <S, T> T copy(S source, T target, Converter converter, BiConsumer<S, T> consumer) {
        if (null == source) {
            return null;
        }

        this.setConverter(null != converter);

        BeanCopier copier = getCopier(source.getClass(), target.getClass());

        if (null == copier) {
            // 以防万一
            copier = BeanCopier.create(source.getClass(), target.getClass(), useConverter);
        }

        copier.copy(source, target, converter);

        if (null != consumer) {
            consumer.accept(source, target);
        }
        return target;
    }


    /**
     * 复制集合对象
     *
     * @param source   源对象
     * @param target   目标对象
     * @param consumer 通用操作
     * @return List<T>
     */
    public <S, T> List<T> copyList(List<S> source, Supplier<T> target, BiConsumer<S, T> consumer) {
        return CollectionUtils.isNotEmpty(source) ? source.stream().map(item -> {
            T t = target.get();
            copy(item, t, null);

            if (null != consumer) {
                consumer.accept(item, t);
            }
            return t;
        }).collect(Collectors.toList()) : Collections.emptyList();
    }
}
