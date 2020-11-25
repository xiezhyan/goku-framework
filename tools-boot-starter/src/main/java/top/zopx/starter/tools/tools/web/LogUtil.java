package top.zopx.starter.tools.tools.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sanq.Yan
 * @date 2020/11/23
 */
public class LogUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);

    private static boolean isInfo() {
        return LOGGER.isInfoEnabled();
    }

    private static boolean isDebug() {
        return LOGGER.isDebugEnabled();
    }

    private static boolean isError() {
        return LOGGER.isErrorEnabled();
    }

    private static boolean isWarn() {
        return LOGGER.isWarnEnabled();
    }

    private static boolean isTrace() {
        return LOGGER.isTraceEnabled();
    }

    public static void info(String msg) {
        if (!isInfo())
            return;

        LOGGER.info(msg);
    }

    public static void info(String msg, Object... args) {
        if (!isInfo())
            return;

        LOGGER.info(msg, args);
    }

    public static void debug(String msg) {
        if (!isDebug())
            return;

        LOGGER.debug(msg);
    }

    public static void debug(String msg, Object... args) {
        if (!isDebug())
            return;

        LOGGER.debug(msg, args);
    }

    public static void error(String msg) {
        if (!isError())
            return;

        LOGGER.error(msg);
    }

    public static void error(String msg, Object... args) {
        if (!isError())
            return;

        LOGGER.error(msg, args);
    }

    public static void warn(String msg) {
        if (!isWarn())
            return;

        LOGGER.warn(msg);
    }

    public static void warn(String msg, Object... args) {
        if (!isWarn())
            return;

        LOGGER.warn(msg, args);
    }

    public static void trace(String msg) {
        if (isTrace())
            return;

        LOGGER.trace(msg);
    }

    public static void trace(String msg, Object... args) {
        if (isTrace())
            return;

        LOGGER.trace(msg, args);
    }
}
