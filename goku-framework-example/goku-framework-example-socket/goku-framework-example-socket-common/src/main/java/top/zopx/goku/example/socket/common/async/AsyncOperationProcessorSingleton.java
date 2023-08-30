package top.zopx.goku.example.socket.common.async;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.netty.execute.AsyncOperationProcessor;

import java.util.concurrent.Executor;

/**
 * 异步操作处理器
 *
 * @author Mr.Xie
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public final class AsyncOperationProcessorSingleton {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncOperationProcessorSingleton.class);

    private AsyncOperationProcessorSingleton() {}


    public static AsyncOperationProcessorSingleton getInstance() {
        return AsyncOperationProcessorSingleton.Holder.INSTANCE;
    }

    private static class Holder {
        public static final AsyncOperationProcessorSingleton INSTANCE = new AsyncOperationProcessorSingleton();
    }

    /**
     * 内置处理器
     */
    private static final AsyncOperationProcessor PROCESS = new AsyncOperationProcessor(
            "bizServer_async_operation",
            -1 // 使用默认的线程数量
    );

    /**
     * 主线程执行器
     */
    private static final Executor MAIN_EXECUTOR = MainThreadPoolExecutorSingleton.getInstance()::process;


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
        PROCESS.process(bindId, op, exec, co);
    }

    /**
     * 处理异步操作,
     * 异步操作执行完成之后会回到主线程处理器继续执行
     *
     * @param bindId 绑定 ( 线程 ) Id
     * @param op     异步操作对象
     * @param co     ( 回到主线程 ) 同步继续执行
     */
    public void process(int bindId, Runnable op,  Runnable co) {
        process(
                bindId, op, MAIN_EXECUTOR, co
        );
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

    /**
     * 执行一步操作
     *
     * @param op 异步操作对象
     */
    public void process(Runnable op) {
        // 执行异步操作
        process(RandomUtils.nextInt(0, 1024), op, null, null);
    }
}
