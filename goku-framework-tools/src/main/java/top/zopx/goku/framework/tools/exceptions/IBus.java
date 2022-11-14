package top.zopx.goku.framework.tools.exceptions;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/18 16:15
 */
public interface IBus {

    /**
     * 获取异常编码
     *
     * @return 编码
     */
    int getCode();

    /**
     * 获取异常信息
     *
     * @return 异常说明
     */
    String getMsg();

    /**
     * 可用于国际化标识
     *
     * @return 可用于国际化标识
     */
    default String getKey() {
        return getMsg();
    }
}
