package top.zopx.starter.mybatis.base;

import org.springframework.web.bind.annotation.*;
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
public abstract class BaseController<Request extends BasicRequest, Response, Entity extends DataEntity> {

    /**
     * 获取服务
     *
     * @return
     */
    abstract IBaseService<Request, Response, Entity> baseService();

    @GetMapping
    public R<Page<Response>> getList(
            Pagination pagination,
            Request request
    ) {
        LongConsumer consumer = null;
        if (null != pagination) {
            consumer = pagination::setTotalCount;
        }

        return R.result(
                new Page<>(pagination, baseService().getList(pagination, request, consumer))
        );
    }

    @GetMapping("/{id}")
    public R<Response> getByPriKey(
            @PathVariable("id") Long id
    ) {
        return R.result(
                baseService().getByPriKey(id)
        );
    }

    @PostMapping
    public R<Boolean> save(@Valid @RequestBody Request request) {
        return R.result(baseService().create(request));
    }

    @PutMapping("/{id}")
    public R<Boolean> updateByPriKey(@Valid @RequestBody Request request, @PathVariable("id") Long id) {
        return R.result(baseService().updateByPriKey(request, id));
    }

    @DeleteMapping("/{id}")
    public R<Boolean> deleteByPriKey(@PathVariable("id") Long id) {
        return R.result(baseService().deleteByPriKey(id));
    }

}
