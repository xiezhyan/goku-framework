package top.zopx.goku.framework.tools.dict;

import java.io.Serializable;

/**
 * key-value枚举实现类
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:06
 */
public record DictEnum<T>(T code, String msg) implements IEnum<T>, Serializable {

    @Override
    public T getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
