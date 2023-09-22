package top.zopx.goku.framework.http.util.binding.interfaces.impl;

import org.springframework.stereotype.Component;
import top.zopx.goku.framework.http.util.binding.interfaces.IBinding;
import top.zopx.goku.framework.tools.dict.IEnum;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/07/11 18:24
 */
@Component
public class EnumBinding implements IBinding<Object, String> {

    @Override
    public String translate(Object origin, Class<? extends IEnum> data, String param, Method method) {
        return Stream.of(data.getEnumConstants())
                .filter(item -> Objects.equals(item.getCode(), origin))
                .map(IEnum::getMsg).findFirst().orElse(null);
    }
}
