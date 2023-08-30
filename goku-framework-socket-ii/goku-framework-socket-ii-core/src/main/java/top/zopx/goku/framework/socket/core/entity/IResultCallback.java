package top.zopx.goku.framework.socket.core.entity;

import top.zopx.goku.framework.tools.entity.wrapper.R;

/**
 * 业务结果回调接口
 *
 * @author Mr.Xie
 * @param <T> 最终结果类型
 */
@FunctionalInterface
public interface IResultCallback<T> {
    /**
     * 执行回调函数
     *
     * @param resultX 业务结果
     */
    void apply(R<T> resultX);
}
