package top.zopx.goku.framework.socket.core.util;

/**
 * 输出额外参数
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/05
 */
public final class Out<T>  {

    private T val;

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    /**
     * 获取
     *
     * @param out    入参
     * @param <T>    泛型
     * @param optVal 默认值
     * @return 内容
     */
    public static <T> T get(Out<T> out, T optVal) {
        return (null == out || null == out.getVal()) ? optVal : out.getVal();
    }

    /**
     * 设置
     *
     * @param out 入参
     * @param val 值
     * @param <T> 泛型
     */
    public static <T> void putVal(Out<T> out, T val) {
        if (null != out) {
            out.setVal(val);
        }
    }

}
