package top.zopx.goku.support.primary.snowflake.service;

import top.zopx.goku.support.primary.core.entity.BootstrapSnowflakeItem;
import top.zopx.goku.support.primary.core.service.IRegisterNodeService;
import top.zopx.goku.support.primary.core.service.impl.BaseIDSnowflakeGetterService;

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
