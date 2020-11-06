package top.zopx.starter.tools.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * top.zopx.starter.tools.annotations.PassTokenSecurityAnnotation
 *
 * 跳过token验证，即跳过登录
 * @author sanq.Yan
 * @date 2020/11/6
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PassTokenSecurityAnnotation {
}
