package top.zopx.goku.framework.primary.snowflake.service;

import top.zopx.goku.framework.primary.core.entity.BootstrapSnowflakeItem;
import top.zopx.goku.framework.primary.core.service.IRegisterNodeService;
import top.zopx.goku.framework.primary.core.service.impl.BaseIDSnowflakeGetterService;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/2/11
 */
public class IDSnowflakeGetterService extends BaseIDSnowflakeGetterService {

    public IDSnowflakeGetterService(IRegisterNodeService registerNodeService, BootstrapSnowflakeItem bootstrapSnowflakeItem) {
        super(registerNodeService, bootstrapSnowflakeItem);
    }

    @Override
    public void destroy() {

    }
}
