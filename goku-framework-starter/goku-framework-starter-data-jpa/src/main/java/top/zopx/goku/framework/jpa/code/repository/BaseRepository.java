package top.zopx.goku.framework.jpa.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import top.zopx.goku.framework.jpa.model.EntityModel;

import java.util.Collection;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/4/4
 */
public interface BaseRepository<T extends EntityModel> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    @Transactional
    @Query("UPDATE #{#entityName} SET isDelete = 1, deleteTime = now() WHERE id = ?1")
    @Modifying
    void delete(Long id);
}
