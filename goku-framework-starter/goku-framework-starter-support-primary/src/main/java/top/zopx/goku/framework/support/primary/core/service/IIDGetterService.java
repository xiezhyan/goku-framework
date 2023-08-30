package top.zopx.goku.framework.support.primary.core.service;

import top.zopx.goku.framework.support.primary.interfaces.ILifecycle;

/**
 * ID生成接口
 *
 * @author Mr.Xie
 * @date 2022/1/17
 * @email xiezhyan@126.com
 */
public interface IIDGetterService extends ILifecycle {

    /**
     * 获取ID
     *
     * @param key 业务主键
     * @return ID
     */
    long getID(String key);

}
