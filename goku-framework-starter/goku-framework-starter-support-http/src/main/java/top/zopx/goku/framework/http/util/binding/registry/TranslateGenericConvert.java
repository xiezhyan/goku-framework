package top.zopx.goku.framework.http.util.binding.registry;

import java.util.function.Function;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/07/11 18:30
 */
public interface TranslateGenericConvert<T>  extends Function<T, Object> {
}
