package top.zopx.goku.framework.mysql.configurator;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.web.util.UserLoginHelper;

import java.time.LocalDateTime;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/3/4
 */
public class CusMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CusMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.debug("save开始填充");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        if (UserLoginHelper.getUserOrNull().isPresent()) {
            this.strictInsertFill(metaObject, "creater", Long.class, UserLoginHelper.getUserOrNull().get().getUserId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.debug("update开始填充");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        if (UserLoginHelper.getUserOrNull().isPresent()) {
            this.strictInsertFill(metaObject, "updater", Long.class, UserLoginHelper.getUserOrNull().get().getUserId());
        }
    }
}
