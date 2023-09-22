package top.zopx.goku.framework.socket.core.selector.strategy;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import top.zopx.goku.framework.socket.core.constant.IKey;
import top.zopx.goku.framework.socket.core.selector.ClientProfile;
import top.zopx.goku.framework.socket.core.selector.Selector;
import top.zopx.goku.framework.socket.core.selector.connect.MultiServerRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 10:01
 */
public class RandomFind implements IFind {
    @Override
    public MultiServerRunner doFind(IKey currJobType) {
        if (null == currJobType) {
            return null;
        }

        List<ClientProfile> tempList = new ArrayList<>();
        for (ClientProfile sp : Selector.getInstance().list()) {
            if (null == sp ||
                    null == sp.getRunner() ||
                    Boolean.FALSE.equals(sp.getRunner().getReady())) {
                continue;
            }

            if (sp.getServerJobTypeSet().contains(currJobType.getType())) {
                tempList.add(sp);
            }
        }

        if (CollectionUtils.isEmpty(tempList)) {
            LOG.error("currJobType = {}: 服务列表为空", currJobType);
            return null;
        }

        ClientProfile profile = tempList.get(RandomUtils.nextInt(0, tempList.size()));
        return profile.getRunner();
    }
}
