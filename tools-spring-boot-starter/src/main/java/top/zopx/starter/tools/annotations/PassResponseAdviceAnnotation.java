package top.zopx.starter.tools.annotations;

import java.lang.annotation.*;

/**
 * 跳过返回结果增强
 *
 * @author sanq.Yan
 * @date 2020/8/11
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PassResponseAdviceAnnotation {
}
