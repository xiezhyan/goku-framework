package top.zopx.starter.tools.annotations;

import java.lang.annotation.*;

/**
 * 跳过全部验证
 *
 * @author sanq.Yan
 * @date 2020/11/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PassAllSecurityAnnotation {
}
