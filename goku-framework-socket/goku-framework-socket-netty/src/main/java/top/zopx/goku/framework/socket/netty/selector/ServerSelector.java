package top.zopx.goku.framework.socket.netty.selector;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.core.util.Out;
import top.zopx.goku.framework.socket.netty.constant.IKey;
import top.zopx.goku.framework.socket.netty.entity.ClientProfile;
import top.zopx.goku.framework.socket.netty.runner.MultiServerRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务服务选择
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public final class ServerSelector {

    private ServerSelector() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSelector.class);

    /**
     * 根据服务器类型随机选择
     *
     * @param currJobType 类型
     * @return Client
     */
    public static MultiServerRunner getServerConnByRandom(IKey currJobType) {
        return getServerConnByRandom(
                currJobType, null
        );
    }

    /**
     * 根据服务器类型随机选择
     *
     * @param currJobType 类型
     * @return Client
     */
    public static MultiServerRunner getServerConnByRandom(IKey currJobType, Out<Long> out) {
        if (null == currJobType) {
            return null;
        }

        // 创建一个临时列表
        List<ClientProfile> tempList = null;

        for (ClientProfile sp : SelectorUtil.get()) {
            if (null == sp ||
                    null == sp.getRunner() ||
                    Boolean.FALSE.equals(sp.getRunner().getReady())) {
                continue;
            }

            if (sp.getServerJobTypeSet().contains(currJobType.getType())) {
                if (null == tempList) {
                    tempList = new ArrayList<>();
                }

                tempList.add(sp);
            }
        }

        if (null == tempList ||
                tempList.isEmpty()) {
            LOGGER.error(
                    "服务器列表为空, expectJobType = {}",
                    currJobType
            );
            return null;
        }

        // 选择一个服务器资料
        int selectedIndex = RandomUtils.nextInt(0, tempList.size());
        ClientProfile profile = tempList.get(selectedIndex);

        // 设置版本号
        Out.putVal(out, SelectorUtil.getRev());
        return profile.getRunner();
    }

    public static MultiServerRunner getServerConnByServerId(int selectServerId) {
        return getServerConnByServerId(
                selectServerId, null
        );
    }

    public static MultiServerRunner getServerConnByServerId(int selectServerId, Out<Long> out) {
        // 获取服务器资料
        ClientProfile sp = SelectorUtil.get(selectServerId);

        if (null == sp ||
                null == sp.getRunner() ||
                Boolean.FALSE.equals(sp.getRunner().getReady())) {
            return null;
        }

        // 设置版本号
        Out.putVal(out, SelectorUtil.getRev());
        return sp.getRunner();
    }

}
