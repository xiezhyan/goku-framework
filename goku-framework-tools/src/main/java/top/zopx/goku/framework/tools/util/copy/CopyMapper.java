package top.zopx.goku.framework.tools.util.copy;

import org.mapstruct.Mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/9
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Mapper(componentModel = "spring")
public @interface CopyMapper {
}
