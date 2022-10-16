package top.zopx.goku.framework.web.util.bind.annotation;

import java.lang.annotation.*;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/07/13 21:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Inherited
public @interface Desensitization {

    /**
     * 开始留N位
     */
    int startIndex();

    /**
     * 最后留N位
     */
    int endIndex();

    /**
     * 指定位置之前的处理
     * <p>邮箱</p>
     */
    String charMaskLast() default "";

    /**
     * 掩盖字符
     */
    String mask() default "*";

}
