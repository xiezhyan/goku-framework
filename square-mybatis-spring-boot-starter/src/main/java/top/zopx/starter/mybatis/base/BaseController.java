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
import java.util.stream.Collectors;

/**
 * 基础Controller
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
public abstract class BaseController<
        VO extends BasicRequest,
        DTO,
        Entity extends DataEntity,
        Service extends IBaseService<DTO, Entity>
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
                        baseService.getList(pagination, convertToDTO(vo), consumer)
                                .stream()
                                .map(this::convertToVO)
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
                convertToVO(baseService.getByPriKey(id))
        );
    }

    @PostMapping
    @OperatorLogAnnotation(value = "保存")
    public R<Boolean> save(@Valid @RequestBody VO vo) {
        return R.result(baseService.create(convertToDTO(vo)));
    }

    @PutMapping("/{id}")
    @OperatorLogAnnotation(value = "通过主键修改")
    public R<Boolean> updateByPriKey(@Valid @RequestBody VO vo, @PathVariable("id") Long id) {
        return R.result(baseService.updateByPriKey(convertToDTO(vo), id));
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
    protected DTO convertToDTO(VO vo) {
        return (DTO) vo;
    }

    /**
     * 将DTO转换为VO
     *
     * @param dto DTO对象
     * @return VO
     */
    protected VO convertToVO(DTO dto) {
        return (VO) dto;
    }
}
