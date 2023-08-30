package top.zopx.goku.framework.http.util.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记需要处理
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/07/11 18:09
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bind {
}
