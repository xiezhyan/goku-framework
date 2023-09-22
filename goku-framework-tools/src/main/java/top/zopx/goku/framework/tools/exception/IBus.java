package top.zopx.goku.framework.tools.exception;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/04/18 16:15
 */
public interface IBus {

    int ERROR_CODE = 500;

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
}
