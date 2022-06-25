package top.zopx.goku.framework.mybatis.base;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.zopx.goku.framework.mybatis.entity.BaseEntity;
import top.zopx.goku.framework.mybatis.entity.DataEntity;
import top.zopx.goku.framework.tools.entity.vo.Sorted;

import java.util.List;

/**
 * 基础Mapper设置
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
public interface IBaseMapper<DO extends DataEntity, DTO extends BaseEntity> extends BaseMapper<DO> {

    /**
     * 查询列表 并且排序
     *
     * @param req 入参
     * @param sorteds 排序
     * @return List<Entity>
     */
    List<DO> getListOrder(
            @Param("req") DTO req,
            @Param("sorts") List<Sorted> sorteds
    );
}
