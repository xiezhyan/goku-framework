package top.zopx.goku.framework.cluster.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 信息上报
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public class Timer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Timer.class);

    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR =
            new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), r -> new Thread(r, "goku-timer-"));
    private Timer() {
    }

    public static void start(Runnable command,
                             long initialDelay,
                             long delay,
                             TimeUnit unit) {
        SCHEDULED_THREAD_POOL_EXECUTOR.scheduleWithFixedDelay(
                new SafeRunner(command), initialDelay, delay, unit
        );
    }

    private  static class SafeRunner implements Runnable {
        /**
         * 内置运行实例
         */
        private final Runnable innerR;

        /**
         * 类参数构造器
         *
         * @param innerR 内置运行实例
         */
        SafeRunner(Runnable innerR) {
            this.innerR = innerR;
        }

        @Override
        public void run() {
            if (null == innerR) {
                return;
            }

            try {
                // 运行
                innerR.run();
            } catch (Exception ex) {
                // 记录错误日志
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }
}
