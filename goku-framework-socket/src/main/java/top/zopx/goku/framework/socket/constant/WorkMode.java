package top.zopx.goku.framework.socket.constant;

/**
 * 工作模式定义
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/30
 */
public final class WorkMode {
    /**
     * 系统环境变量关键字
     */
    static private final String SYSTEM_ENV_KEY = "WORK_MODE";

    /**
     * 开发模式
     */
    static private final String MODE_DEV = "DEV";

    /**
     * 正式模式
     */
    static private final String MODE_OFFICIAL = "OFFICIAL";

    /**
     * 获取当前工作模式
     *
     * @return 当前工作模式
     */
    static public String currWorkMode() {
        String currEnv = System.getenv(SYSTEM_ENV_KEY);
        return (null == currEnv) ? MODE_OFFICIAL : currEnv;
    }

    /**
     * 当前是否是开发模式
     *
     * @return true = 开发模式, false = 其它模式
     */
    static public boolean currIsDevMode() {
        return MODE_DEV.equalsIgnoreCase(System.getenv(SYSTEM_ENV_KEY));
    }
}
