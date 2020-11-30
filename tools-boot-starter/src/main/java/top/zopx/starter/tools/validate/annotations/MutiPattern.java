package top.zopx.starter.tools.validate.annotations;

import top.zopx.starter.tools.validate.listeners.MutiPatternValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多正则匹配
 * @author sanq.Yan
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
        AND,
        OR
    }
}
