package top.zopx.starter.mybatis.base;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.zopx.starter.mybatis.entity.DataEntity;
import top.zopx.starter.tools.basic.BasicRequest;
import top.zopx.starter.tools.basic.Sorted;

import java.util.List;

/**
 * 基础Mapper设置
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
public interface IBaseMapper<Entity extends DataEntity, QueryRequest extends BasicRequest> extends BaseMapper<Entity> {

    /**
     * 查询列表 并且排序
     *
     * @param request 入参
     * @param sorteds 排序
     * @return List<Entity>
     */
    List<Entity> getListOrder(
            @Param("request") QueryRequest request,
            @Param("sorted") List<Sorted> sorteds
    );
}
