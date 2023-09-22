package top.zopx.goku.framework.support.primary.snowflake.service;

import top.zopx.goku.framework.support.primary.core.service.impl.BaseIDSnowflakeGetterService;
import top.zopx.goku.framework.support.primary.core.entity.BootstrapSnowflakeItem;
import top.zopx.goku.framework.support.primary.core.service.IRegisterNodeService;

/**
 * @author Mr.Xie
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
