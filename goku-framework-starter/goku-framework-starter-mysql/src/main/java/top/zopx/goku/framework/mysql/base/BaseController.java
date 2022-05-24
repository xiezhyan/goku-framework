package top.zopx.goku.framework.mysql.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zopx.goku.framework.log.annotations.OperatorLogAnnotation;
import top.zopx.goku.framework.mysql.entity.DataEntity;
import top.zopx.goku.framework.tools.entity.vo.BaseEntity;
import top.zopx.goku.framework.tools.entity.vo.Page;
import top.zopx.goku.framework.tools.entity.vo.Pagination;
import top.zopx.goku.framework.tools.entity.wrapper.R;

import javax.validation.Valid;
import java.util.function.LongConsumer;
import java.util.stream.Collectors;

/**
 * 基础Controller
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
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

    @GetMapping
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

    @GetMapping("/{id}")
    @OperatorLogAnnotation(value = "获取数据详情")
    public R<VO> getByPriKey(
            @PathVariable("id") Long id
    ) {
        return R.result(
                convertDtoToVO(baseService.getByPriKey(id))
        );
    }

    @PostMapping
    @OperatorLogAnnotation(value = "保存")
    public R<Boolean> save(@Valid @RequestBody VO vo) {
        return R.result(baseService.create(convertVoToDTO(vo)));
    }

    @PutMapping("/{id}")
    @OperatorLogAnnotation(value = "通过主键修改")
    public R<Boolean> updateByPriKey(@Valid @RequestBody VO vo, @PathVariable("id") Long id) {
        return R.result(baseService.updateByPriKey(convertVoToDTO(vo), id));
    }

    @DeleteMapping("/{id}")
    @OperatorLogAnnotation(value = "通过主键删除")
    public R<Boolean> deleteByPriKey(@PathVariable("id") Long id) {
        return R.result(baseService.deleteByPriKey(id));
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
