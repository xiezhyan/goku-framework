package top.zopx.goku.framework.socket.core.config.constant;

/**
 * @author Mr.Xie
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
