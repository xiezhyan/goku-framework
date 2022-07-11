package top.zopx.goku.framework.web.bind.interfaces.impl;

import org.springframework.stereotype.Component;
import top.zopx.goku.framework.tools.constant.IEnum;
import top.zopx.goku.framework.web.bind.interfaces.IBinding;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/07/11 18:24
 */
@Component
public class EnumBinding implements IBinding<Object> {

    @Override
    public String translate(Object origin, Class<? extends IEnum> data, String param) {
        return Stream.of(data.getEnumConstants())
                .filter(item -> Objects.equals(item.getCode(), origin))
                .map(IEnum::getMsg).findFirst().orElse(null);
    }
}
