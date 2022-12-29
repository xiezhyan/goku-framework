package top.zopx.goku.framework.biz.constant;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/18 16:15
 */
public interface IServerCommandLineKey {

    /**
     * getOpt
     *
     * @return String
     */
    String getOpt();

    /**
     * getLongOpt
     *
     * @return String
     */
    String getLongOpt();

    /**
     * getDesc
     *
     * @return String
     */
    String getDesc();

    /**
     * isHasRequire
     *
     * @return boolean
     */
    boolean isHasRequire();

}
