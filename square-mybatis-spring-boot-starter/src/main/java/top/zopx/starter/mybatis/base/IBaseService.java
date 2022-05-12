package top.zopx.starter.mybatis.base;


import com.baomidou.mybatisplus.extension.service.IService;
import top.zopx.starter.mybatis.entity.DataEntity;
import top.zopx.starter.tools.basic.BasicRequest;
import top.zopx.starter.tools.basic.Pagination;

import java.util.List;
import java.util.function.LongConsumer;

/**
 * 基础服务
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
public interface IBaseService<Request extends BasicRequest, Response, Entity extends DataEntity> extends IService<Entity> {

    /**
     * 获取模块列表 分页
     *
     * @param pagination 分页参数
     * @param request    查询参数
     * @param consumer   分页结果
     * @return List<Response>
     */
    List<Response> getList(
            Pagination pagination,
            Request request,
            LongConsumer consumer
    );

    /**
     * 新增
     *
     * @param request 入参
     * @return 是否新增成功
     */
    Boolean create(Request request);

    /**
     * 修改
     *
     * @param request 入参
     * @param id      主键
     * @return 是否修改成功
     */
    Boolean updateByPriKey(Request request, Long id);

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
    Response getByPriKey(Long id);

}
