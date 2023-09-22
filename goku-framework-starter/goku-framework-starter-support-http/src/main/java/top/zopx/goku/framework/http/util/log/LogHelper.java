package top.zopx.goku.framework.http.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.WeakHashMap;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:43
 */
@Deprecated
public class LogHelper {
    private static final LogHelper LOG = new LogHelper();

    private static Logger logger = null;

    private static final WeakHashMap<Class<?>, Logger> WEAK_HASH_MAP = new WeakHashMap<>();

    public static LogHelper getLogger(Class<?> clazz) {
        synchronized (LOG) {
            logger = WEAK_HASH_MAP.putIfAbsent(clazz, LoggerFactory.getLogger(clazz));
            if (Objects.isNull(logger)) {
                logger = WEAK_HASH_MAP.getOrDefault(clazz, LoggerFactory.getLogger(clazz));
            }
        }
        return LOG;
    }

    private boolean isInfo() {
        if (Objects.isNull(logger)) {
            return false;
        }
        return !logger.isInfoEnabled();
    }

    private boolean isDebug() {
        if (Objects.isNull(logger)) {
            return false;
        }
        return !logger.isDebugEnabled();
    }

    private boolean isError() {
        if (Objects.isNull(logger)) {
            return false;
        }
        return !logger.isErrorEnabled();
    }

    private boolean isWarn() {
        if (Objects.isNull(logger)) {
            return false;
        }
        return !logger.isWarnEnabled();
    }

    private boolean isTrace() {
        if (Objects.isNull(logger)) {
            return false;
        }
        return !logger.isTraceEnabled();
    }

    public void info(String msg) {
        if (!this.isInfo()) {
            logger.info(msg);
        }
    }

    public void info(String msg, Object... args) {
        if (!this.isInfo()) {
            logger.info(msg, args);
        }
    }

    public void debug(String msg) {
        if (!this.isDebug()) {
            logger.debug(msg);
        }
    }

    public void debug(String msg, Object... args) {
        if (!this.isDebug()) {
            logger.debug(msg, args);
        }
    }

    public void error(String msg) {
        if (!this.isError()) {
            logger.error(msg);
        }
    }

    public void error(String msg, Object... args) {
        if (!this.isError()) {
            logger.error(msg, args);
        }
    }

    public void error(String msg, Throwable t) {
        if (!this.isInfo()) {
            logger.error(msg, t);
        }
    }

    public void warn(String msg) {
        if (!this.isWarn()) {
            logger.warn(msg);
        }
    }

    public void warn(String msg, Object... args) {
        if (!this.isWarn()) {
            logger.warn(msg, args);
        }
    }

    public void trace(String msg) {
        if (!this.isTrace()) {
            logger.trace(msg);
        }
    }

    public void trace(String msg, Object... args) {
        if (!this.isTrace()) {
            logger.trace(msg, args);
        }
    }
}
