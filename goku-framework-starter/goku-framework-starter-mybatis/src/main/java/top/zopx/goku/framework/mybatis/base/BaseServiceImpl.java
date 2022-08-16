package top.zopx.goku.framework.mybatis.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import org.springframework.transaction.annotation.Transactional;
import top.zopx.goku.framework.mybatis.constant.ErrorCodeCons;
import top.zopx.goku.framework.mybatis.entity.BaseEntity;
import top.zopx.goku.framework.mybatis.entity.DataEntity;
import top.zopx.goku.framework.tools.entity.vo.Pagination;
import top.zopx.goku.framework.tools.entity.vo.Sorted;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.mybatis.util.UserLoginHelper;

import java.time.LocalDateTime;
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
public abstract class BaseServiceImpl<VO, DTO extends BaseEntity, DO extends DataEntity, Mapper extends IBaseMapper<DO, DTO>>
        extends ServiceImpl<Mapper, DO>
        implements IBaseService<VO, DTO, DO>, IHookService<DTO, DO> {

    @Override
    public List<VO> getList(Pagination pagination, DTO query, LongConsumer consumer) {
        Page<DO> page = null;
        List<Sorted> sorteds = null;
        if (null != pagination) {
            page = PageMethod.startPage(pagination.getCurrentIndex(), pagination.getPageSize());
            sorteds = pagination.getSorteds();
        }
        doSearchBefore(query);
        List<DO> list = baseMapper.getListOrder(query, sorteds);
        if (null != consumer && null != page) {
            consumer.accept(page.getTotal());
            page.close();
        }

        return list.stream().map(this::copyToVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(DTO body) {
        DO entity = copyToEntity(body);
        doCreateBefore(entity, body);
        if (baseMapper.insert(entity) == 1) {
            return doCreateAfter(entity, body);
        }
        throw new BusException(ErrorCodeCons.ERROR_CREATE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByPriKey(DTO body, Long id) {
        DO entity = getById(id);
        // 需要额外处理的操作，钩子函数
        copyNotNullForRequest(body, entity);
        doUpdateBefore(entity, body);
        if (baseMapper.updateById(entity) == 1) {
            return doUpdateAfter(entity, body);
        }
        throw new BusException(ErrorCodeCons.ERROR_UPDATE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByPriKey(Long id) {
        DO data = doDeleteBefore(id);
        if (Objects.nonNull(data)) {
            data.setDeleteTime(LocalDateTime.now());
            data.setDeleter(UserLoginHelper.getUserIdOrNull());
            return baseMapper.deleteById(data) == 1;
        } else {
            return baseMapper.deleteById(id) == 1;
        }
    }

    @Override
    public VO getByPriKey(Long id) {
        return copyToVO(getById(id));
    }

    /**
     * 通过ID获取Entity
     *
     * @param id ID
     * @return DO
     */
    private DO getById(Long id) {
        return Optional.ofNullable(baseMapper.selectById(id))
                .orElseThrow(() -> new BusException(ErrorCodeCons.NOT_ENTITY));
    }

    /**
     * 将Entity转换为Response
     *
     * @param data Entity参数
     * @return Response
     */
    protected abstract VO copyToVO(DO data);

    /**
     * 将Request转换为Entity
     *
     * @param dto 入参
     * @return Entity
     */
    protected abstract DO copyToEntity(DTO dto);

    /**
     * 将非空的Request转换为Entity
     *
     * @param body 入参
     * @param data data
     */
    protected abstract void copyNotNullForRequest(DTO body, DO data);
}
