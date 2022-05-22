package top.zopx.goku.framework.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 租户条件获取
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:25
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnoSearchByTenantId {
}