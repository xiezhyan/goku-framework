package top.zopx.starter.distribution.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加锁注解
 * @author sanq.Yan
 * @date 2021/3/28
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AnnotationDistribution {
    String key() default "lock";
}
