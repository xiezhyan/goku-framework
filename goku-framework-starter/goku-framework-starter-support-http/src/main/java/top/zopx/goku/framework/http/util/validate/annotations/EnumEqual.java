package top.zopx.goku.framework.http.util.validate.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import top.zopx.goku.framework.http.util.validate.listeners.EnumEqualValidator;
import top.zopx.goku.framework.tools.dict.DictEnum;
import top.zopx.goku.framework.tools.dict.IEnum;

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
@Constraint(validatedBy = EnumEqualValidator.class)
public @interface EnumEqual {

    String message() default "";

    Class<? extends IEnum> datasource() default DictEnum.class;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
