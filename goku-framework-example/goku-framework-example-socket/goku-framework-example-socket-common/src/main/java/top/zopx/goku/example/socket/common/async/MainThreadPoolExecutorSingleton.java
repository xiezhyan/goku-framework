package top.zopx.goku.example.socket.common.async;

import com.google.protobuf.GeneratedMessageV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.biz.recognizer.BaseCmdHandlerFactory;
import top.zopx.goku.framework.netty.bind.handler.BaseCmdHandleContext;
import top.zopx.goku.framework.biz.execute.MainThreadPoolExecutor;

/**
 * 异步执行
 *
 * @author 俗世游子
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public final class MainThreadPoolExecutorSingleton {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MainThreadPoolExecutorSingleton.class);

    private static final MainThreadPoolExecutor EXECUTOR = new MainThreadPoolExecutor("goku-example", new BaseCmdHandlerFactory() {
        @Override
        public String getScanPackage() {
            return "top.zopx.goku.example.socket.biz.cmd";
        }
    });

    private MainThreadPoolExecutorSingleton() {}

    public static MainThreadPoolExecutorSingleton getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final MainThreadPoolExecutorSingleton INSTANCE = new MainThreadPoolExecutorSingleton();
    }

    public void process(Runnable runnable) {
        EXECUTOR.process(runnable);
    }

    public void process(BaseCmdHandleContext ctx, GeneratedMessageV3 cmdMsg) {
        EXECUTOR.process(ctx, cmdMsg);
    }
}
