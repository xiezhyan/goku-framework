package top.zopx.goku.framework.send.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/6/7
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TopicScan {
    /**
     * basePackage
     * @return basePackage
     */
    String basePackage() default "";
}
