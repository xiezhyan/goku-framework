package top.zopx.starter.tools.tools.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sanq.Yan
 * @date 2020/11/23
 */
public final class LogUtil {

    private static Logger LOGGER = null;

    private LogUtil(Class<?> clazz) {
        LOGGER = LoggerFactory.getLogger(clazz);
    }

    private volatile static LogUtil INSTANCE = null;

    /**
     * 单例
     * @param clazz 需要使用的对象
     * @return
     */
    public static LogUtil getInstance(Class<?> clazz) {
        if (null == INSTANCE) {
            synchronized (LogUtil.class) {
                if (null == INSTANCE) {
                    INSTANCE = new LogUtil(clazz);
                }
            }
        }
        return INSTANCE;
    }

    private boolean isInfo() {
        return !LOGGER.isInfoEnabled();
    }

    private boolean isDebug() {
        return !LOGGER.isDebugEnabled();
    }

    private boolean isError() {
        return !LOGGER.isErrorEnabled();
    }

    private boolean isWarn() {
        return !LOGGER.isWarnEnabled();
    }

    private boolean isTrace() {
        return !LOGGER.isTraceEnabled();
    }

    public void info(String msg) {
        if (isInfo()) {
            return;
        }

        LOGGER.info(msg);
    }

    public void info(String msg, Object... args) {
        if (isInfo()) {
            return;
        }

        LOGGER.info(msg, args);
    }

    public void debug(String msg) {
        if (isDebug()) {
            return;
        }

        LOGGER.debug(msg);
    }

    public void debug(String msg, Object... args) {
        if (isDebug()) {
            return;
        }

        LOGGER.debug(msg, args);
    }

    public void error(String msg) {
        if (isError()) {
            return;
        }

        LOGGER.error(msg);
    }

    public void error(String msg, Object... args) {
        if (isError()) {
            return;
        }

        LOGGER.error(msg, args);
    }

    public void warn(String msg) {
        if (isWarn()) {
            return;
        }

        LOGGER.warn(msg);
    }

    public void warn(String msg, Object... args) {
        if (isWarn()) {
            return;
        }

        LOGGER.warn(msg, args);
    }

    public void trace(String msg) {
        if (isTrace()) {
            return;
        }

        LOGGER.trace(msg);
    }

    public void trace(String msg, Object... args) {
        if (isTrace()) {
            return;
        }

        LOGGER.trace(msg, args);
    }
}
