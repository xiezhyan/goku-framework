package top.zopx.goku.framework.mybatis.base;


import com.baomidou.mybatisplus.extension.service.IService;
import top.zopx.goku.framework.mybatis.entity.BaseEntity;
import top.zopx.goku.framework.mybatis.entity.DataEntity;
import top.zopx.goku.framework.tools.entity.vo.Pagination;

import java.util.List;
import java.util.function.LongConsumer;

/**
 * 基础服务
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
public interface IBaseService<VO, DTO extends BaseEntity, DO extends DataEntity> extends IService<DO> {

    /**
     * 获取模块列表 分页
     *
     * @param pagination 分页参数
     * @param query    查询参数
     * @param consumer   分页结果
     * @return List<Response>
     */
    List<VO> getList(
            Pagination pagination,
            DTO query,
            LongConsumer consumer
    );

    /**
     * 新增
     *
     * @param body 入参
     * @return 是否新增成功
     */
    Boolean create(DTO body);

    /**
     * 修改
     *
     * @param body 入参
     * @param id      主键
     * @return 是否修改成功
     */
    Boolean updateByPriKey(DTO body, Long id);

    /**
     * 逻辑删除
     *
     * @param id 主键
     * @return 是否删除成功
     */
    Boolean deleteByPriKey(Long id);

    /**
     * 通过主键查询
     *
     * @param id 主键
     * @return Response
     */
    VO getByPriKey(Long id);
}
