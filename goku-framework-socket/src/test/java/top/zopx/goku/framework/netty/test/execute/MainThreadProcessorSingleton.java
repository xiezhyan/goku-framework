package top.zopx.goku.framework.netty.test.execute;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.biz.recognizer.BaseCmdHandlerFactory;
import top.zopx.goku.framework.netty.execute.MainThreadPoolExecutor;

/**
 * 线程执行
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public class MainThreadProcessorSingleton {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainThreadProcessorSingleton.class);

    private final MainThreadPoolExecutor mainThreadPoolExecutor;

    private MainThreadProcessorSingleton() {
        mainThreadPoolExecutor = new MainThreadPoolExecutor("biz-server", new BaseCmdHandlerFactory() {
            @Override
            public String getScanPackage() {
                return "top.zopx.goku.framework.netty.test.handler";
            }
        });
    }

    public static MainThreadProcessorSingleton getInstance() {
        return Holder.INSTANCE;
    }

    public void process(ChannelHandlerContext ctx, int remoteSessionId, int fromUserId, GeneratedMessageV3 protoMsg) {
        mainThreadPoolExecutor.process(ctx, remoteSessionId, fromUserId, protoMsg);
    }

    public void process(Runnable task) {
        mainThreadPoolExecutor.process(task);
    }

    public static class Holder {
        public static final MainThreadProcessorSingleton INSTANCE = new MainThreadProcessorSingleton();
    }
}
