package top.zopx.goku.framework.socket.core.cmd.execute;

import com.google.protobuf.GeneratedMessageV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.core.cmd.ICmdHandle;
import top.zopx.goku.framework.socket.core.cmd.ICmdHandleFactory;
import top.zopx.goku.framework.socket.core.cmd.context.BaseCmdHandleContext;
import top.zopx.goku.framework.socket.core.entity.SafeRunner;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步执行
 * <pre>
 * {@code
 *  public final class MainThreadPoolExecutorSingleton {
 *
 *     private static final Logger LOGGER = LoggerFactory.getLogger(MainThreadPoolExecutorSingleton.class);
 *
 *     private static final MainThreadPoolExecutor EXECUTOR = new MainThreadPoolExecutor("goku-example", new BaseCmdHandlerFactory() {
 *         @Override
 *         public String getScanPackage() {
 *             return "top.zopx.goku.example.socket.biz.cmd";
 *         }
 *     });
 *
 *     private MainThreadPoolExecutorSingleton() {}
 *
 *     public static MainThreadPoolExecutorSingleton getInstance() {
 *         return Holder.INSTANCE;
 *     }
 *
 *     private static class Holder {
 *         public static final MainThreadPoolExecutorSingleton INSTANCE = new MainThreadPoolExecutorSingleton();
 *     }
 *
 *     public void process(Runnable runnable) {
 *         EXECUTOR.process(runnable);
 *     }
 *
 *     public void process(BaseCmdHandleContext ctx, GeneratedMessageV3 cmdMsg) {
 *         EXECUTOR.process(ctx, cmdMsg);
 *     }
 * }
 * }
 * </pre>
 * @author Mr.Xie
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public final class MainThreadPoolExecutor {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MainThreadPoolExecutor.class);

    /**
     * 线程服务：通过单线程来执行业务
     */
    private final ExecutorService executorService;

    /**
     * 消息处理类工厂
     */
    private final ICmdHandleFactory factory;

    public MainThreadPoolExecutor(String threadName, ICmdHandleFactory factory) {
        executorService = new ThreadPoolExecutor(
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

        this.factory = factory;
    }

    public void process(BaseCmdHandleContext ctx, GeneratedMessageV3 cmdMsg) {
        if (null == ctx ||
                null == cmdMsg) {
            return;
        }

        this.process(() -> {
            // 获取命令类
            final Class<?> cmdClazz = cmdMsg.getClass();
            final ICmdHandle<? extends BaseCmdHandleContext, ? extends GeneratedMessageV3> cmdHandler =
                    factory.create(cmdClazz);

            if (null == cmdHandler) {
                LOGGER.error(
                        "未找到命令处理器, cmdClazz = {}",
                        cmdClazz
                );
                return;
            }

            LOGGER.debug(
                    "处理命令, cmdClazz = {}",
                    cmdClazz.getName()
            );

            cmdHandler.cmd(castCtx(ctx), castMsg(cmdMsg));
        });
    }

    private <CTX extends BaseCmdHandleContext> CTX castCtx(BaseCmdHandleContext ctx) {
        return (CTX) ctx;
    }

    private <T extends GeneratedMessageV3> T castMsg(GeneratedMessageV3 cmdMsg) {
        return (T) cmdMsg;
    }

    /**
     * 业务执行
     *
     * @param runnable 执行程序
     */
    public void process(Runnable runnable) {
        if (null != runnable) {
            executorService.submit(
                    new SafeRunner(runnable)
            );
        }
    }
}
