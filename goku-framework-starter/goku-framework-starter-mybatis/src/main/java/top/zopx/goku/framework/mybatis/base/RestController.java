package top.zopx.goku.framework.mybatis.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.zopx.goku.framework.log.annotation.OperatorLogAnnotation;
import top.zopx.goku.framework.mybatis.entity.BaseEntity;
import top.zopx.goku.framework.mybatis.entity.DataEntity;
import top.zopx.goku.framework.tools.entity.vo.Page;
import top.zopx.goku.framework.tools.entity.vo.Pagination;
import top.zopx.goku.framework.tools.entity.wrapper.R;
import top.zopx.goku.framework.web.util.validate.constant.ValidGroup;

import javax.validation.groups.Default;
import java.util.function.LongConsumer;

/**
 * 基于Restful风格Controller
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
@Validated
public abstract class RestController<
        VO,
        DTO extends BaseEntity,
        DO extends DataEntity,
        Service extends IBaseService<VO, DTO, DO>
        > {

    /**
     * 获取服务
     */
    @Autowired
    protected Service baseService;

    @GetMapping
    @OperatorLogAnnotation(value = "获取数据列表")
    public R<Page<VO>> getList(Pagination pagination, DTO query) {
        LongConsumer consumer = null;
        if (pagination.getCurrentIndex() > 0 && pagination.getPageSize() > 0) {
            consumer = pagination::setTotalCount;
        }
        return R.result(
                new Page<>(pagination, baseService.getList(pagination, query, consumer))
        );
    }

    @GetMapping("/{id}")
    @OperatorLogAnnotation(value = "获取数据详情")
    public R<VO> getByPriKey(@PathVariable(value = "id") Long id) {
        return R.result(baseService.getByPriKey(id));
    }


    @PostMapping
    @OperatorLogAnnotation(value = "保存")
    public R<Boolean> save(@Validated(value = {Default.class, ValidGroup.Create.class}) @RequestBody DTO body) {
        return R.result(baseService.create(body));
    }

    @PutMapping("/{id}")
    @OperatorLogAnnotation(value = "通过主键修改")
    public R<Boolean> updateByPriKey(@PathVariable("id") Long id, @Validated(value = {Default.class, ValidGroup.Update.class}) @RequestBody DTO body) {
        return R.result(baseService.updateByPriKey(body, id));
    }

    @DeleteMapping("/{id}")
    @OperatorLogAnnotation(value = "通过主键删除")
    public R<Boolean> deleteByPriKey(@PathVariable("id") Long id) {
        return R.result(baseService.deleteByPriKey(id));
    }
}
