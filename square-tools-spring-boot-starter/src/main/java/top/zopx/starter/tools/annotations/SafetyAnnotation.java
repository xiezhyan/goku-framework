package top.zopx.starter.tools.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注接口鉴权KEY
 * 如果不采用Security框架，那么在鉴权使用的时候就会用到该注解
 *
 * @author 俗世游子
 * @date 2020/11/17
 */

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SafetyAnnotation {

    /**
     * key
     */
    String[] key() default {};
}
