package top.zopx.goku.framework.cluster.constant;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/02 13:09
 */
public interface IErrorBus {
    /**
     * 是否成功
     *
     * @return Boolean
     */
    Boolean getSuccess();

    /**
     * 返回内容
     *
     * @return String
     */
    String getMessage();

    /**
     * 返回编码
     *
     * @return Integer
     */
    Integer getCode();

}
