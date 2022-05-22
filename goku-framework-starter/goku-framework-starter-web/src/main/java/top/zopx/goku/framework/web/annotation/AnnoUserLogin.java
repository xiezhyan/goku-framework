package top.zopx.goku.framework.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过 HandlerMethodArgumentResolver 的方式获取到登录用户信息
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:22
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnoUserLogin {
}