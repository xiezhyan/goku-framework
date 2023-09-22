package top.zopx.goku.framework.socket.netty.execute;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 异步操作处理器
 *
 * 消息处理器，用来处理消息、编码、消息内容间的关系
 * <pre>
 * {@code
 *
 * public final class AsyncOperationProcessorSingleton {
 *
 *     private static final Logger LOGGER = LoggerFactory.getLogger(AsyncOperationProcessorSingleton.class);
 *
 *     private AsyncOperationProcessorSingleton() {}
 *
 *
 *     public static AsyncOperationProcessorSingleton getInstance() {
 *         return AsyncOperationProcessorSingleton.Holder.INSTANCE;
 *     }
 *
 *     private static class Holder {
 *         public static final AsyncOperationProcessorSingleton INSTANCE = new AsyncOperationProcessorSingleton();
 *     }
 *
 *
 *     private static final AsyncOperationProcessor PROCESS = new AsyncOperationProcessor(
 *             "bizServer_async_operation",
 *             -1
 *     );
 *
 *     private static final Executor MAIN_EXECUTOR = MainThreadPoolExecutorSingleton.getInstance()::process;
 *
 *     public void process(int bindId, Runnable op,  Runnable co) {
 *         process(
 *                 bindId, op, MAIN_EXECUTOR, co
 *         );
 *     }
 *
 * }
 *
 * }
 * </pre>
 * @author Mr.Xie
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public final class AsyncOperationProcessor {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncOperationProcessor.class);

    /**
     * 线程服务数组
     */
    private final ExecutorService[] esArray;

    /**
     * 类默认构造器
     */
    public AsyncOperationProcessor() {
        this(null, 0);
    }

    /**
     * 类参数构造器
     *
     * @param processorName 处理器名称
     * @param threadCount   线程数量
     */
    public AsyncOperationProcessor(String processorName, int threadCount) {
        if (null == processorName) {
            processorName = "AsyncOperationProcessor";
        }

        if (threadCount <= 0) {
            threadCount = Runtime.getRuntime().availableProcessors() << 2;
        }

        esArray = new ExecutorService[threadCount];

        for (int i = 0; i < threadCount; i++) {
            // 线程名称
            final String threadName = processorName + "[" + i + "]";
            // 创建单线程服务
            esArray[i] = new ThreadPoolExecutor(
                    1, 1,
                    60L, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(Runtime.getRuntime().availableProcessors() << 4),
                    r -> {
                        // 创建线程并起个名字
                        Thread t = new Thread(r);
                        t.setName(threadName);
                        return t;
                    }
            );
        }
    }

    /**
     * 处理异步操作,
     * 1、通过 bindId 来选择一个异步线程;
     * 2、执行 op 操作;
     * 3、op 执行完成之后将 co 扔给 exec 线程继续执行；
     *
     * @param bindId 绑定 ( 线程 ) Id
     * @param op     异步操作对象
     * @param exec   同步执行线程
     *               Executor mainThreadExecutor = (cmd) -> new MainThreadPoolExecutor().process(cmd);
     *               ctx.executor()
     * @param co     同步继续执行
     */
    public void process(int bindId, Runnable op, Executor exec, Runnable co) {
        if (null == op) {
            return;
        }

        // 根据绑定 Id 获取线程索引
        bindId = Math.abs(bindId);
        int esIndex = bindId % esArray.length;

        esArray[esIndex].submit(() -> {
            try {
                // 执行异步操作
                op.run();

                if (null != exec &&
                        null != co) {
                    // 回到主消息线程继续执行完成逻辑
                    exec.execute(co);
                }
            } catch (Exception ex) {
                // 记录错误日志
                LOGGER.error(ex.getMessage(), ex);
            }
        });
    }

    /**
     * 处理异步操作
     *
     * @param bindId 绑定 ( 线程 ) Id
     * @param op     异步操作
     */
    public void process(int bindId, Runnable op) {
        process(bindId, op, null, null);
    }

    public void process(Runnable op) {
        process(RandomUtils.nextInt(0, 1024), op, null, null);
    }
}
