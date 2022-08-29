package top.zopx.goku.framework.web.util.bind.interfaces;

import top.zopx.goku.framework.tools.constant.IEnum;

/**
 * 消息处理接口
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/07/11 18:16
 */
public interface IBinding<T, K> {

    /**
     * 处理
     *
     * @param origin    目标值
     * @param data      枚举数据源
     * @param param     额外参数
     * @param condition 执行条件
     * @return K
     */
    K translate(T origin, Class<? extends IEnum> data, String param, String[] condition);

}
