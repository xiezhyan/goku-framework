package top.zopx.goku.framework.jpa.model;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.http.util.login.UserLoginHelper;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Entity 自动注入
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/3/4
 */
@Component
public class AutoPersistListener {

    @PrePersist
    public void prePersist(EntityModel entity) {
        // 在保存之前设置相关字段的值
        entity.setIsDelete(0);
        entity.setCreater(UserLoginHelper.getUserId());
        entity.setCreateTime(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(EntityModel entity) {
        // 在修改之前设置相关字段的值
        if (Objects.equals(1, entity.getIsDelete())) {
            entity.setDeleter(UserLoginHelper.getUserId());
            entity.setDeleteTime(LocalDateTime.now());
        } else {
            entity.setUpdater(UserLoginHelper.getUserId());
            entity.setUpdateTime(LocalDateTime.now());
        }
    }
}
