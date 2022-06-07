package top.zopx.goku.framework.send.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Topic
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/06/07
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Topic {
    /**
     * Topic
     *
     * @return Topic
     */
    String topic() default "";

    /**
     * Tag
     *
     * @return Tag
     */
    String tag() default "";

    /**
     * Group
     *
     * @return Group
     */
    String group() default "${spring.application.name}";
}
