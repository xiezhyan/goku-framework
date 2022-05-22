package top.zopx.goku.framework.web.util.validate.listeners;

import org.apache.commons.collections4.CollectionUtils;
import top.zopx.goku.framework.web.util.validate.annotations.MutiPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author 俗世游子
 * @date 2020/11/19
 */
public class MutiPatternValidator implements ConstraintValidator<MutiPattern, String> {

    private MutiPattern.Logic logic;
    private List<String> patterns;

    @Override
    public void initialize(MutiPattern constraintAnnotation) {
        logic = constraintAnnotation.logic();
        patterns = Arrays.asList(constraintAnnotation.patterns());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (CollectionUtils.isEmpty(patterns)) {
            return false;
        }

        if (logic == MutiPattern.Logic.OR) {
            for (String pattern : patterns) {
                if (Pattern.matches(pattern, s)) {
                    return true;
                }
            }
        } else if (logic == MutiPattern.Logic.AND) {
            for (String pattern : patterns) {
                if (!Pattern.matches(pattern, s)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
