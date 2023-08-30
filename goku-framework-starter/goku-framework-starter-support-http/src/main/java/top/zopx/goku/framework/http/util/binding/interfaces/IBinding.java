package top.zopx.goku.framework.http.util.binding.interfaces;

import top.zopx.goku.framework.tools.dict.IEnum;

import java.lang.reflect.Method;

/**
 * 消息处理接口
 *
 * @author Mr.Xie
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
     * @param method    执行方法
     * @return K
     */
    K translate(T origin, Class<? extends IEnum> data, String param, Method method);

}
