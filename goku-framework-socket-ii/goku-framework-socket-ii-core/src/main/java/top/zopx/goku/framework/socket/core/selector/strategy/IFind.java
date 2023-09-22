package top.zopx.goku.framework.socket.core.selector.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.core.constant.IKey;
import top.zopx.goku.framework.socket.core.selector.connect.MultiServerRunner;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 09:54
 */
public interface IFind {
    Logger LOG = LoggerFactory.getLogger(IFind.class);

    /**
     * 查找指定服务器
     *
     * @param currJobType 职责
     * @return MultiServerRunner
     */
    MultiServerRunner doFind(IKey currJobType);

}
