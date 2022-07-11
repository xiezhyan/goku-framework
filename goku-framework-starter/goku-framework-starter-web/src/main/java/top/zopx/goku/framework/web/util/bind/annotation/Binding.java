package top.zopx.goku.framework.web.util.bind.annotation;

import top.zopx.goku.framework.tools.constant.DictEnum;
import top.zopx.goku.framework.tools.constant.IEnum;
import top.zopx.goku.framework.web.util.bind.interfaces.IBinding;
import top.zopx.goku.framework.web.util.bind.interfaces.impl.EnumBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源设置
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/07/11 18:13
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Binding {

    /**
     * 源字段
     */
    String value();

    /**
     * 枚举数据源
     */
    Class<? extends IEnum> dataSource() default DictEnum.class;

    /**
     * 转换方式
     */
    Class<? extends IBinding> translate() default EnumBinding.class;

    /**
     * 额外参数
     */
    String param() default "";

}
