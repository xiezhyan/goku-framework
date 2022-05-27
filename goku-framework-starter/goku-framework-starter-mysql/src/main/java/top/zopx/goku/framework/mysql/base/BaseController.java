package top.zopx.goku.framework.mysql.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.zopx.goku.framework.log.annotations.OperatorLogAnnotation;
import top.zopx.goku.framework.mysql.entity.BaseEntity;
import top.zopx.goku.framework.mysql.entity.DataEntity;
import top.zopx.goku.framework.tools.entity.vo.Page;
import top.zopx.goku.framework.tools.entity.vo.Pagination;
import top.zopx.goku.framework.tools.entity.wrapper.R;
import top.zopx.goku.framework.web.util.validate.constant.ValidGroup;

import javax.validation.groups.Default;
import java.util.function.LongConsumer;
import java.util.stream.Collectors;

/**
 * 基础Controller
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
@Validated
public abstract class BaseController<
        VO extends BaseEntity,
        DTO,
        Entity extends DataEntity,
        Service extends IBaseService<DTO, Entity>
        > {

    /**
     * 获取服务
     */
    @Autowired
    protected Service baseService;

    @GetMapping("/page")
    @OperatorLogAnnotation(value = "获取数据列表")
    public R<Page<VO>> getList(
            Pagination pagination,
            VO vo
    ) {
        LongConsumer consumer = null;
        if (null != pagination) {
            consumer = pagination::setTotalCount;
        }
        return R.result(
                new Page<>(
                        pagination,
                        baseService.getList(pagination, convertVoToDTO(vo), consumer)
                                .stream()
                                .map(this::convertDtoToVO)
                                .collect(Collectors.toList())
                )
        );
    }

    @GetMapping("/detail")
    @OperatorLogAnnotation(value = "获取数据详情")
    public R<VO> getByPriKey(@RequestParam(value = "id", required = true) Long id) {
        return R.result(
                convertDtoToVO(baseService.getByPriKey(id))
        );
    }

    @PostMapping("/save")
    @OperatorLogAnnotation(value = "保存")
    public R<Boolean> save(@Validated(value = {Default.class, ValidGroup.Create.class}) @RequestBody VO vo) {
        return R.result(baseService.create(convertVoToDTO(vo)));
    }

    @PutMapping("/update")
    @OperatorLogAnnotation(value = "通过主键修改")
    public R<Boolean> updateByPriKey(@Validated(value = {Default.class, ValidGroup.Update.class}) @RequestBody VO vo) {
        return R.result(baseService.updateByPriKey(convertVoToDTO(vo), vo.getId()));
    }

    @DeleteMapping("/delete")
    @OperatorLogAnnotation(value = "通过主键删除")
    public R<Boolean> deleteByPriKey(@Validated(value = {ValidGroup.Delete.class}) @RequestBody VO vo) {
        return R.result(baseService.deleteByPriKey(vo.getId()));
    }

    /**
     * 将VO转换为DTO
     *
     * @param vo VO对象
     * @return DTO
     */
    protected abstract DTO convertVoToDTO(VO vo);

    /**
     * 将DTO转换为VO
     *
     * @param dto DTO对象
     * @return VO
     */
    protected abstract VO convertDtoToVO(DTO dto);
}
