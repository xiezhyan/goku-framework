package top.zopx.starter.tools.tools.copy;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 复制对象
 * @author sanq.Yan
 * @date 2020/4/28
 */
public class BeanCopyUtil extends BeanUtils {

    /**
     * 忽略为null的字段的复制
     *
     * @param source 源
     * @param target 目标
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * 复制List集合
     *
     * @param sourceList 源list
     * @param target     目标类
     * @param <T>        T 返回类型
     * @param <K>        K 源数据类型
     * @return List
     */
    public static <T, K> List<T> copyListProperties(List<K> sourceList, Supplier<T> target) {
        return copyListPropertiesIgnoreNull(sourceList, target, false, null);
    }

    /**
     * 复制List集合
     *
     * @param sourceList 源list
     * @param target     目标类
     * @param consumer   回调函数 Lambda中BiConsumer
     * @param <T>        T 返回类型
     * @param <K>        K 源数据类型
     * @return List
     */
    public static <T, K> List<T> copyListProperties(List<K> sourceList, Supplier<T> target, BiConsumer<T, K> consumer) {
        return copyListPropertiesIgnoreNull(sourceList, target, false, consumer);
    }

    /**
     * 复制List集合
     *
     * @param sourceList 源list
     * @param target     目标类
     * @param <T>        T 返回类型
     * @param <K>        K 源数据类型
     * @return List
     */
    public static <T, K> List<T> copyListPropertiesIgnoreNull(List<K> sourceList, Supplier<T> target) {
        return copyListPropertiesIgnoreNull(sourceList, target, true, null);
    }

    /**
     * 复制List集合
     *
     * @param sourceList 源list
     * @param target     目标类
     * @param consumer   回调函数 Lambda中BiConsumer
     * @param <T>        T 返回类型
     * @param <K>        K 源数据类型
     * @return List
     */
    public static <T, K> List<T> copyListPropertiesIgnoreNull(List<K> sourceList, Supplier<T> target, BiConsumer<T, K> consumer) {
        return copyListPropertiesIgnoreNull(sourceList, target, true, consumer);
    }

    /**
     * 处理List拷贝操作
     * @param sourceList 源list
     * @param target     目标类
     * @param isIgnoreNull   是否忽略空属性
     * @param consumer   回调函数 Lambda中BiConsumer
     * @return List<T>
     */
    private static <T, K> List<T> copyListPropertiesIgnoreNull(List<K> sourceList, Supplier<T> target, boolean isIgnoreNull, BiConsumer<T, K> consumer) {
        if (CollectionUtils.isEmpty(sourceList))
            return Collections.emptyList();

        return sourceList.stream().map(k -> {
            T t = target.get();
            if (isIgnoreNull)
                copyPropertiesIgnoreNull(k, t);
            else
                copyProperties(k, t);

            if (null != consumer)
                consumer.accept(t, k);

            return t;
        }).collect(Collectors.toList());
    }

    /**
     * 忽略为null的属性
     * @param source 源目标
     * @return String[]
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);

    }
}
