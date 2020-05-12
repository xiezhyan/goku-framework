package top.zopx.starter.upload.util;

import lombok.SneakyThrows;

import java.util.concurrent.*;

/**
 * top.zopx.starter.upload.util.Async
 *
 * @author sanq.Yan
 * @date 2020/5/12
 */
public class Async {

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() / 4;
    private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * int corePoolSize,    线程池长期维持的线程数，即使线程处于Idle状态，也不会回收。
     * int maximumPoolSize, 线程数的上限
     * long keepAliveTime,  超过corePoolSize的线程的idle时长，  超过这个时间，多余的线程会被回收。
     * TimeUnit unit,
     * BlockingQueue<Runnable> workQueue,   任务的排队队列
     * ThreadFactory threadFactory          新线程的产生方式
     * RejectedExecutionHandler handler     拒绝策略
     */

    private static ThreadPoolExecutor executor = null;

    @SneakyThrows
    public static <T> T get(Callable<T> callable) {
        executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, POOL_SIZE, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(512),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        Future<T> future = executor.submit(callable);
        return future.get();
    }

    public static void shutdown() {
        executor.shutdown();
    }
}
