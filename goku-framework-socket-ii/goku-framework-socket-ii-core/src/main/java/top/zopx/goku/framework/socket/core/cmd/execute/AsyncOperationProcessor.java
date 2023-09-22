package top.zopx.goku.framework.socket.core.cmd.execute;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 异步操作处理器
 *
 * 消息处理器，用来处理消息、编码、消息内容间的关系
 *
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

    public static AsyncOperationProcessor getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final AsyncOperationProcessor INSTANCE = new AsyncOperationProcessor();
    }

    public AsyncOperationProcessor() {
            int threadCount = Runtime.getRuntime().availableProcessors() << 2;

        esArray = new ExecutorService[threadCount];

        for (int i = 0; i < threadCount; i++) {
            // 线程名称
            final String threadName = "goku-framework-socket-ii-biz-async[" + i + "]";
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
