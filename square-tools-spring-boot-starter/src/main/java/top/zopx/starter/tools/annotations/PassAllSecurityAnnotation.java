package top.zopx.starter.tools.annotations;

import java.lang.annotation.*;

/**
 * 跳过全部验证
 *
 * @author 俗世游子
 * @date 2020/11/16
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PassAllSecurityAnnotation {
}
