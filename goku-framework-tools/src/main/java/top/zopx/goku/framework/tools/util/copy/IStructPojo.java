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
public interface IStructPojo<DO, VO, DTO> extends IStruct<DTO, VO> {

    /**
     * 原始类型转返回类型
     *
     * @param data 原始类型
     * @return 返回类型
     */
    VO copyDataToVO(DO data);

    /**
     * DTO 转 Data
     *
     * @param dto DTO
     * @return Data
     */
    DO copyDtoToData(DTO dto);

    /**
     * 原始类型转返回类型
     *
     * @param data 原始类型
     * @return List<DTO>
     */
    List<VO> copyDataToVOList(List<DO> data);

    /**
     * 忽略空值的copy
     *
     * @param dto  源对象
     * @param d copy对象
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copyDtoToDoIgnoreNull(DTO dto, @MappingTarget DO d);
}
