package top.zopx.starter.mybatis.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import org.springframework.transaction.annotation.Transactional;
import top.zopx.starter.mybatis.constant.ErrorCodeCons;
import top.zopx.starter.mybatis.entity.DataEntity;
import top.zopx.starter.tools.basic.BasicRequest;
import top.zopx.starter.tools.basic.Pagination;
import top.zopx.starter.tools.basic.Sorted;
import top.zopx.starter.tools.exceptions.BusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.LongConsumer;

/**
 * 基础服务实现
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
public abstract class BaseServiceImpl<Request  extends BasicRequest, Response, Entity extends DataEntity, Mapper extends IBaseMapper<Entity, Request>>
        extends ServiceImpl<Mapper, Entity>
        implements IBaseService<Request, Response, Entity>, IHookService<Request, Entity> {

    @Override
    public List<Response> getList(Pagination pagination, Request request, LongConsumer consumer) {
        Page<Module> page = null;
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

        return list.stream().map(this::copyToResponse).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(Request request) {
        doCreateBefore(request);
        Entity entity = copyToEntity(request);
        if (baseMapper.insert(entity) == 1) {
            return doCreateAfter(entity, request);
        }
        throw new BusException(ErrorCodeCons.ERROR_CREATE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByPriKey(Request request, Long id) {
        Entity entity =
                Optional.ofNullable(baseMapper.selectById(id))
                        .orElseThrow(() -> new BusException(ErrorCodeCons.NOT_ENTITY));
        copyNotNullForRequest(request, entity);
        // 需要额外处理的操作，钩子函数
        doUpdateBefore(entity, request);
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
    public Response getByPriKey(Long id) {
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
    protected abstract Response copyToResponse(Entity data);

    /**
     * 将Request转换为Entity
     *
     * @param request 入参
     * @return Entity
     */
    protected abstract Entity copyToEntity(Request request);

    /**
     * 将非空的Request转换为Entity
     *
     * @param request 入参
     */
    protected abstract void copyNotNullForRequest(Request request, Entity data);
}
