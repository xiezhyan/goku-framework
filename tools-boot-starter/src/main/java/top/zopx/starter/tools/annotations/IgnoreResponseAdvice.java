package top.zopx.starter.tools.annotations;

import java.lang.annotation.*;

/**
 * top.zopx.wisdom.edu.tool.common.configurator.advice.IgnoreResponseAdvice
 *
 * @author sanq.Yan
 * @date 2020/8/11
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreResponseAdvice {
}
