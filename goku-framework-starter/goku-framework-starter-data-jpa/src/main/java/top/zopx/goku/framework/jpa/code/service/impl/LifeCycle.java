package top.zopx.goku.framework.jpa.code.service.impl;

import top.zopx.goku.framework.http.entity.dto.EntityDTO;
import top.zopx.goku.framework.http.entity.vo.EntityVO;
import top.zopx.goku.framework.jpa.model.EntityModel;

import java.util.Collection;
import java.util.List;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/4/3
 */
public interface LifeCycle<E extends EntityDTO, T extends EntityModel, V extends EntityVO> {

    default void startToPage(E body, T entity) {}

    default void stopToPage(List<V> bodyList) {

    }

    default void startToSave(E body, T data) {}

    default void stopToSave(E body, T data) {}

    default void startToUpdate(E body, T data) {}

    default void stopToUpdate(E body, T data) {}

    default void startToDelete(Collection<Long> data) {}

    default void startToDelete(Long id) {}

    default void stopToDelete(Long id) {}

    default void stopToDelete(Collection<Long> data) {}

    default void stopToGetById(V body) {}
}
