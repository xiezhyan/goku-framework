package top.zopx.goku.framework.http.util.binding.annotation;

import top.zopx.goku.framework.http.util.binding.interfaces.IBinding;
import top.zopx.goku.framework.http.util.binding.interfaces.impl.EnumBinding;
import top.zopx.goku.framework.tools.dict.DictEnum;
import top.zopx.goku.framework.tools.dict.IEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源设置
 *
 * @author Mr.Xie
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
