package top.zopx.starter.mybatis.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zopx.starter.log.annotations.OperatorLogAnnotation;
import top.zopx.starter.mybatis.entity.DataEntity;
import top.zopx.starter.tools.basic.BasicRequest;
import top.zopx.starter.tools.basic.Page;
import top.zopx.starter.tools.basic.Pagination;
import top.zopx.starter.tools.basic.R;

import javax.validation.Valid;
import java.util.function.LongConsumer;

/**
 * 基础Controller
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
public abstract class BaseController<
        Request extends BasicRequest,
        Response,
        Entity extends DataEntity,
        Service extends IBaseService<Request, Response, Entity>
> {

    /**
     * 获取服务
     *
     * @return IBaseService
     */
    @Autowired
    protected Service baseService;

    @GetMapping
    @OperatorLogAnnotation(value = "获取数据列表")
    public R<Page<Response>> getList(
            Pagination pagination,
            Request request
    ) {
        LongConsumer consumer = null;
        if (null != pagination) {
            consumer = pagination::setTotalCount;
        }

        return R.result(
                new Page<>(pagination, baseService.getList(pagination, request, consumer))
        );
    }

    @GetMapping("/{id}")
    @OperatorLogAnnotation(value = "获取数据详情")
    public R<Response> getByPriKey(
            @PathVariable("id") Long id
    ) {
        return R.result(
                baseService.getByPriKey(id)
        );
    }

    @PostMapping
    @OperatorLogAnnotation(value = "保存")
    public R<Boolean> save(@Valid @RequestBody Request request) {
        return R.result(baseService.create(request));
    }

    @PutMapping("/{id}")
    @OperatorLogAnnotation(value = "通过主键修改")
    public R<Boolean> updateByPriKey(@Valid @RequestBody Request request, @PathVariable("id") Long id) {
        return R.result(baseService.updateByPriKey(request, id));
    }

    @DeleteMapping("/{id}")
    @OperatorLogAnnotation(value = "通过主键删除")
    public R<Boolean> deleteByPriKey(@PathVariable("id") Long id) {
        return R.result(baseService.deleteByPriKey(id));
    }

}
