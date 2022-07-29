package top.zopx.goku.framework.netty.test.execute;

import top.zopx.goku.framework.netty.execute.AsyncOperationProcessor;

import java.util.Random;
import java.util.concurrent.Executor;

/**
 * 异步操作处理器单例类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public final class AsyncOperationProcessorSingleton {
    /**
     * 单例对象
     */
    static private final AsyncOperationProcessorSingleton INSTANCE = new AsyncOperationProcessorSingleton();

    /**
     * 随机对象
     */
    private volatile Random rand;

    /**
     * 内置处理器
     */
    private final AsyncOperationProcessor innerProcessor = new AsyncOperationProcessor(
        "bizServer_asyncOperationProcessor",
        -1
    );

    /**
     * 主线程执行器
     */
    private final Executor mainThreadExecutor = (cmd) -> MainThreadProcessorSingleton.getInstance().process(cmd);

    /**
     * 私有化类默认构造器
     */
    private AsyncOperationProcessorSingleton() {
    }

    /**
     * 获取单例对象
     *
     * @return 单例对象
     */
    public static  AsyncOperationProcessorSingleton getInstance() {
        return INSTANCE;
    }

    /**
     * 处理异步操作
     *
     * @param bindId 绑定 ( 线程 ) Id
     * @param op     异步操作对象
     * @param exec   同步执行线程
     * @param co     同步继续执行
     */
    public void process(int bindId, Runnable op, Executor exec, Runnable co) {
        innerProcessor.process(bindId, op, exec, co);
    }

    /**
     * 处理异步操作,
     * 异步操作执行完成之后会回到主线程处理器继续执行
     *
     * @param bindId 绑定 ( 线程 ) Id
     * @param op     异步操作对象
     * @param co     ( 回到主线程 ) 同步继续执行
     */
    public void process(int bindId, Runnable op, Runnable co) {
        innerProcessor.process(
            bindId, op, mainThreadExecutor, co
        );
    }

    /**
     * 处理异步操作
     *
     * @param bindId 绑定 ( 线程 ) Id
     * @param op     异步操作对象
     */
    public void process(int bindId, Runnable op) {
        innerProcessor.process(bindId, op);
    }

    /**
     * 执行一步操作
     *
     * @param op 异步操作对象
     */
    public void process(Runnable op) {
        if (null == rand) {
            synchronized (this) {
                if (null == rand) {
                    rand = new Random();
                }
            }
        }

        // 随机一个 Id
        int bindId = rand.nextInt(1024);
        // 执行异步操作
        process(bindId, op, null, null);
    }
}
