package top.zopx.goku.framework.biz.result;

import top.zopx.goku.framework.cluster.entity.R;

/**
 * 业务结果回调接口
 *
 * @author 俗世游子
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
