package top.zopx.goku.framework.tools.util.copy;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * 基础转换
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/3/9
 */
public interface IStruct<S, T> {

    /**
     * 原始类型转返回类型
     *
     * @param s 原始类型
     * @return 返回类型
     */
    T copyToT(S s);

    /**
     * DTO 转 Data
     *
     * @param t DTO
     * @return S
     */
    S copyToS(T t);

    /**
     * 原始类型转返回类型
     *
     * @param data 原始类型
     * @return List<T>
     */
    List<T> copyToTaList(List<S> data);

    /**
     * 原始类型转返回类型
     * @param data 原始类型
     * @return List<S>
     */
    List<S> copyToSoList(List<T> data);

    /**
     * 忽略空值的copy
     *
     * @param s 源对象
     * @param t copy对象
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copyIgnoreNull(S s, @MappingTarget T t);
}
