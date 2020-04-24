package top.zopx.starter.tools.annotations;

import java.lang.annotation.*;

/**
 * com.sanq.product.cab.annotations.AuthorityAnnotation
 *
 * @author sanq.Yan
 * @date 2020/2/27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorityAnnotation {
    String[] keys() default {};
}
