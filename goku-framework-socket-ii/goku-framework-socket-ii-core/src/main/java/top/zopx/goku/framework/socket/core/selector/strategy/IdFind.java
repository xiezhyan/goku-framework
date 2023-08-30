package top.zopx.goku.framework.socket.core.selector.strategy;

import top.zopx.goku.framework.socket.core.constant.IKey;
import top.zopx.goku.framework.socket.core.selector.ClientProfile;
import top.zopx.goku.framework.socket.core.selector.Selector;
import top.zopx.goku.framework.socket.core.selector.connect.MultiServerRunner;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 10:20
 */
public class IdFind implements IFind {

    private final Integer serverId;

    public IdFind(Integer serverId) {
        this.serverId = serverId;
    }

    @Override
    public MultiServerRunner doFind(IKey currJobType) {
        ClientProfile profile = Selector.getInstance().getInstanceById(serverId);

        if (null == profile) {
            LOG.error("未通过{}查询到指定服务", serverId);
            return null;
        }

        MultiServerRunner runner = profile.getRunner();
        if (null == runner || !runner.getReady()) {
            LOG.error("服务 {} 未准备", serverId);
            Selector.getInstance().removeById(serverId);
            return null;
        }

        return runner;
    }
}
