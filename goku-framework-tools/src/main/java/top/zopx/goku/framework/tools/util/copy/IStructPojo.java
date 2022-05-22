package top.zopx.goku.framework.tools.util.copy;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * 基础转换
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/3/9
 */
public interface IStructPojo<D, V, T> {

    /**
     * 原始类型转返回类型
     *
     * @param d 原始类型
     * @return 返回类型
     */
    T copyDataToDTO(D d);

    /**
     * DTO 转 Data
     *
     * @param dto DTO
     * @return Data
     */
    D copyDtoToData(T dto);

    /**
     * 原始类型转返回类型
     *
     * @param data 原始类型
     * @return List<DTO>
     */
    List<T> copyDataToDTOList(List<D> data);

    /**
     * 忽略空值的copy
     *
     * @param dto  源对象
     * @param d copy对象
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copyIgnoreNull(T dto, @MappingTarget D d);

    /**
     * VO 转 DTO
     *
     * @param vo VO
     * @return DTO
     */
    T copyVoToDTO(V vo);

    /**
     * DTO 转 VO
     *
     * @param dto DTO
     * @return VO
     */
    V copyDtoToVO(T dto);
}
