package top.zopx.goku.framework.socket.core.selector.strategy;

import org.apache.commons.lang3.ArrayUtils;
import top.zopx.goku.framework.socket.core.constant.IKey;
import top.zopx.goku.framework.socket.core.selector.connect.MultiServerRunner;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 10:31
 */
public class CompositeFind implements IFind {

    private final IFind[] findArray;

    public CompositeFind(IFind... findArray) {
        this.findArray = findArray;
    }

    @Override
    public MultiServerRunner doFind(IKey currJobType) {
        if (ArrayUtils.isEmpty(findArray)) {
            return null;
        }

        MultiServerRunner instance;
        for (IFind find : findArray) {
            instance = find.doFind(currJobType);
            if (null != instance) {
                return instance;
            }
        }

        return null;
    }
}
