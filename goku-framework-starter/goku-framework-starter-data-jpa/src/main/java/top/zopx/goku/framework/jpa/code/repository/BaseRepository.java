package top.zopx.goku.framework.jpa.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.zopx.goku.framework.jpa.model.EntityModel;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/4/4
 */
public interface BaseRepository<T extends EntityModel> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
