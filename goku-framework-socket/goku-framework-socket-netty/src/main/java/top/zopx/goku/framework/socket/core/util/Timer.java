package top.zopx.goku.framework.socket.core.util;

import top.zopx.goku.framework.socket.core.entity.SafeRunner;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 信息上报
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public class Timer {

    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR =
            new ScheduledThreadPoolExecutor(
                    Runtime.getRuntime().availableProcessors(),
                    r -> new Thread(r, "goku-timer-schedule")
            );
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

    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                            long initialDelay,
                                                            long delay,
                                                            TimeUnit unit) {
        return SCHEDULED_THREAD_POOL_EXECUTOR.scheduleWithFixedDelay(
                new SafeRunner(command), initialDelay, delay, unit
        );
    }
}
