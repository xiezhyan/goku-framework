package top.zopx.goku.framework.http.util.validate.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import top.zopx.goku.framework.http.util.validate.listeners.MutiPatternValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多正则匹配
 * @author Mr.Xie
 * @date 2020/11/19
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MutiPatternValidator.class)
public @interface MutiPattern {

    String message() default "";

    String[] patterns() default {};

    Logic logic() default Logic.AND;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    enum Logic {
        /**
         * 全部通过才算成功
         */
        AND,
        /**
         * 只要有一个通过就可以
         */
        OR
    }
}
