package top.zopx.goku.framework.mybatis.configurator;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import top.zopx.goku.framework.mybatis.util.UserLoginHelper;

import java.time.LocalDateTime;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/3/4
 */
@Configuration
public class CusMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CusMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.debug("save开始填充");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        // 相当于这里也做了一次校验工作
        this.strictInsertFill(metaObject, "creater", Long.class, UserLoginHelper.getUserId());
        this.strictInsertFill(metaObject, "isDelete", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.debug("update开始填充");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        // 相当于这里也做了一次校验工作
        this.strictInsertFill(metaObject, "updater", Long.class, UserLoginHelper.getUserId());
    }
}
