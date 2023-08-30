package top.zopx.goku.framework.socket.core.selector.strategy;

import top.zopx.goku.framework.socket.core.constant.IKey;
import top.zopx.goku.framework.socket.core.selector.ClientProfile;
import top.zopx.goku.framework.socket.core.selector.Selector;
import top.zopx.goku.framework.socket.core.selector.connect.MultiServerRunner;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 10:25
 */
public class LeastLastFind implements IFind {

    @Override
    public MultiServerRunner doFind(IKey currJobType) {
        if (null == currJobType) {
            return null;
        }

        double maxLoadCount = Double.MAX_VALUE;
        MultiServerRunner minLoadInstance = null;
        for (ClientProfile sp : Selector.getInstance().list()) {
            if (null == sp ||
                    null == sp.getRunner() ||
                    Boolean.FALSE.equals(sp.getRunner().getReady())) {
                continue;
            }

            if (sp.getServerJobTypeSet().contains(currJobType.getType())
                    && sp.getLoadCount() < maxLoadCount) {
                minLoadInstance = sp.getRunner();
                maxLoadCount = sp.getLoadCount();
            }
        }

        return minLoadInstance;
    }
}
