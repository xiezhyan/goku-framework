package top.zopx.goku.framework.tools.constant;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:06
 */
public class DictEnum<T> implements IEnum<T> {
    /**
     * 数据字典编码
     */
    private final T code;

    /**
     * 数据字典描述
     */
    private final String msg;

    public DictEnum(T code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public T getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
