package top.zopx.goku.framework.mybatis.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import org.springframework.transaction.annotation.Transactional;
import top.zopx.goku.framework.mybatis.constant.ErrorCodeCons;
import top.zopx.goku.framework.mybatis.entity.DataEntity;
import top.zopx.goku.framework.tools.entity.vo.Pagination;
import top.zopx.goku.framework.tools.entity.vo.Sorted;
import top.zopx.goku.framework.tools.exceptions.BusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.LongConsumer;
import java.util.stream.Collectors;

/**
 * 基础服务实现
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
public abstract class BaseServiceImpl<DTO, Entity extends DataEntity, Mapper extends IBaseMapper<Entity, DTO>>
        extends ServiceImpl<Mapper, Entity>
        implements IBaseService<DTO, Entity>, IHookService<DTO, Entity> {

    @Override
    public List<DTO> getList(Pagination pagination, DTO request, LongConsumer consumer) {
        Page<Entity> page = null;
        List<Sorted> sorteds = null;
        if (null != pagination) {
            page = PageMethod.startPage(pagination.getCurrentIndex(), pagination.getPageSize());
            sorteds = pagination.getSorteds();
        }
        doSearchBefore(request);
        List<Entity> list = baseMapper.getListOrder(request, sorteds);
        if (null != consumer && null != page) {
            consumer.accept(page.getTotal());
        }

        return list.stream().map(this::copyToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(DTO request) {
        Entity entity = copyToEntity(request);
        doCreateBefore(entity, request);
        if (baseMapper.insert(entity) == 1) {
            return doCreateAfter(entity, request);
        }
        throw new BusException(ErrorCodeCons.ERROR_CREATE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByPriKey(DTO request, Long id) {
        Entity entity =
                Optional.ofNullable(baseMapper.selectById(id))
                        .orElseThrow(() -> new BusException(ErrorCodeCons.NOT_ENTITY));
        // 需要额外处理的操作，钩子函数
        doUpdateBefore(entity, request);
        copyNotNullForRequest(request, entity);
        if (baseMapper.updateById(entity) == 1) {
            return doUpdateAfter(entity);
        }
        throw new BusException(ErrorCodeCons.ERROR_UPDATE);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByPriKey(Long id) {
        Entity data = doDeleteBefore(id);
        if (Objects.nonNull(data)) {
            return baseMapper.deleteById(data) == 1;
        } else {
            return baseMapper.deleteById(id) == 1;
        }
    }

    @Override
    public DTO getByPriKey(Long id) {
        Entity entity =
                Optional.ofNullable(baseMapper.selectById(id))
                        .orElseThrow(() -> new BusException(ErrorCodeCons.NOT_ENTITY));
        return copyToResponse(entity);
    }

    /**
     * 将Entity转换为Response
     *
     * @param data Entity参数
     * @return Response
     */
    protected abstract DTO copyToResponse(Entity data);

    /**
     * 将Request转换为Entity
     *
     * @param dto 入参
     * @return Entity
     */
    protected abstract Entity copyToEntity(DTO dto);

    /**
     * 将非空的Request转换为Entity
     *
     * @param dto 入参
     */
    protected abstract void copyNotNullForRequest(DTO dto, Entity data);
}
