package top.zopx.goku.framework.jpa.code.service.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import top.zopx.goku.framework.http.constant.ErrorEnum;
import top.zopx.goku.framework.http.entity.dto.EntityDTO;
import top.zopx.goku.framework.http.entity.vo.EntityVO;
import top.zopx.goku.framework.http.util.login.UserLoginHelper;
import top.zopx.goku.framework.jpa.code.repository.BaseRepository;
import top.zopx.goku.framework.jpa.code.service.IService;
import top.zopx.goku.framework.jpa.model.EntityModel;
import top.zopx.goku.framework.tools.entity.vo.Pagination;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.util.copy.IStructMapping;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/4/2 11:14
 */
public abstract class BaseServiceImpl
        <E extends EntityDTO,
                T extends EntityModel,
                R extends BaseRepository<T>,
                V extends EntityVO>
        implements IService<E, V>, LifeCycle<E, T, V> {

    @Autowired
    private R r;

    public R getR() {
        return r;
    }

    protected T getById(Long id) {
        return r.findById(id).orElseThrow(() -> new BusException(ErrorEnum.NOT_ENTITY));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(E body) {
        T entity = struct().copyToT(body);
        startToSave(body, entity);
        r.saveAndFlush(entity);
        stopToSave(body, entity);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(Collection<E> data) {
        if (CollectionUtils.isEmpty(data)) {
            throw new BusException(ErrorEnum.ERROR_CREATE);
        }
        List<T> entityList = data.stream()
                .map(item -> struct().copyToT(item))
                .toList();
        r.saveAllAndFlush(entityList);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(E body, Long id) {
        T entity = getById(id);
        startToUpdate(body, entity);
        struct().copyIgnoreNull(body, entity);
        entity.setId(id);
        r.saveAndFlush(entity);
        stopToUpdate(body, entity);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id) {
        startToDelete(id);
        r.delete(id);
        stopToDelete(id);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Collection<Long> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Boolean.TRUE;
        }
        startToDelete(data);
        data.forEach(this.r::delete);
        stopToDelete(data);
        return Boolean.TRUE;
    }

    @Override
    public V get(Long id) {
        V body = struct().copyToV(getById(id));
        stopToGetById(body);
        return body;
    }

    @Override
    public List<V> list(E body) {
        List<T> list = r.findAll(buildQuerySpec(body));
        List<V> data = list.stream().map(item -> struct().copyToV(item)).toList();
        stopToPage(data);
        return data;
    }

    @Override
    public List<V> list(E body, Pagination pagination) {
        PageRequest pageRequest = buildPageRequest(pagination);
        Page<T> page = r.findAll(buildQuerySpec(body), pageRequest);
        return buildPage(page, pagination);
    }

    @NotNull
    protected static PageRequest buildPageRequest(Pagination pagination) {
        int currentIndex = Optional.ofNullable(pagination.getCurrentIndex()).orElse(1);
        int pageSize = Optional.ofNullable(pagination.getPageSize()).orElse(1000);
        pagination.setCurrentIndex(currentIndex);
        pagination.setPageSize(pageSize);

        PageRequest pageRequest =
                PageRequest.of(Math.max(0, currentIndex - 1), Math.min(pageSize, 1000));
        if (null != pagination.getSorted()) {
            pageRequest = pageRequest.withSort(
                    Sort.by(
                            pagination.getSorted().stream()
                                    .map(item ->
                                            Sort.Order.by(item.getField())
                                                    .with(Sort.Direction.valueOf(item.getOrderBy().toUpperCase())))
                                    .toList()
                    )
            );
        }
        return pageRequest;
    }

    protected List<V> buildPage(Page<T> page, Pagination pagination) {
        pagination.setTotalCount(page.getTotalElements());
        List<V> data = page.getContent().stream().map(item -> struct().copyToV(item)).toList();
        stopToPage(data);
        return data;
    }

    /**
     * 构建查询条件
     *
     * @param body 入参
     * @return Specification<T>
     */
    protected Specification<T> buildQuerySpec(E body) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 不删除数据
            predicates.add(
                    criteriaBuilder.equal(root.get("isDelete"), 0)
            );

            BaseServiceImpl.this.toPredicate(body, root, query, criteriaBuilder, predicates);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 查询条件
     *
     * @param body            参数
     * @param root            root
     * @param query           query
     * @param criteriaBuilder criteriaBuilder
     * @return List<Predicate>
     */
    protected void toPredicate(E body, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> queryList) {
    }

    protected abstract IStructMapping<E, V, T> struct();
}
