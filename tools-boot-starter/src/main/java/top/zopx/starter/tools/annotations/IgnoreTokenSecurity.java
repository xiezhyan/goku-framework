package top.zopx.starter.tools.annotations;

import java.lang.annotation.*;

/**
 * version: 忽略Token检查
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreTokenSecurity {

}
