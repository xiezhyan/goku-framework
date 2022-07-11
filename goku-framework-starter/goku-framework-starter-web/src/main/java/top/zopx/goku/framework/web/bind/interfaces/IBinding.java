package top.zopx.goku.framework.web.bind.interfaces;

import top.zopx.goku.framework.tools.constant.IEnum;

/**
 * 消息处理接口
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/07/11 18:16
 */
public interface IBinding<T> {

    /**
     * 处理
     *
     * @param origin 目标值
     * @param data   枚举数据源
     * @param param  额外参数
     * @return K
     */
    String translate(T origin, Class<? extends IEnum> data, String param);

}
