//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package top.zopx.starter.tools.tools.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.WeakHashMap;

/**
 * 日志输出类
 *
 * @author 俗世游子
 * @date 2022/05/16
 * @email xiezhyan@126.com
 */
public final class LogUtil {

    private static final LogUtil LOG = new LogUtil();

    private static Logger logger = null;

    private static final WeakHashMap<Class<?>, Logger> WEAK_HASH_MAP = new WeakHashMap<>();

    public static LogUtil getLogger(Class<?> clazz) {
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
