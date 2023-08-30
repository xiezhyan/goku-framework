package top.zopx.goku.framework.http.util.validate.listeners;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import top.zopx.goku.framework.http.util.validate.annotations.EnumEqual;
import top.zopx.goku.framework.tools.dict.IEnum;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mr.Xie
 * @date 2020/11/19
 */
public class EnumEqualValidator implements ConstraintValidator<EnumEqual, Integer> {

    private Class<? extends IEnum> datasource;
    @Override
    public void initialize(EnumEqual constraintAnnotation) {
        datasource = constraintAnnotation.datasource();
    }

    @Override
    public boolean isValid(Integer val, ConstraintValidatorContext constraintValidatorContext) {
        return Stream.of(datasource.getEnumConstants())
                .anyMatch(item -> Objects.equals(item.getCode(), val));
    }
}
