package top.zopx.goku.framework.support.primary.interfaces;

/**
 * @author Mr.Xie
 * @date 2021/11/19
 * @email xiezhyan@126.com
 */
public interface ILifecycle {

    /**
     * 初始化操作
     */
    void init();

    /**
     * 销毁操作
     */
    void destroy();

}
