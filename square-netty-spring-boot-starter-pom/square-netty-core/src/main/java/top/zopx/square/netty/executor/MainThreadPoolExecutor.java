package top.zopx.square.netty.executor;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.square.netty.handle.ICmdHandler;
import top.zopx.square.netty.handle.ICusChannelHandlerContext;
import top.zopx.square.netty.handle.ICusCmdHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步执行
 *
 * @author 俗世游子
 * @date 2022/2/4
 * @email xiezhyan@126.com
 */
public final class MainThreadPoolExecutor {

    /**
     * 日志对象
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(MainThreadPoolExecutor.class);

    /**
     * 线程服务：通过单线程来执行业务
     */
    private final ExecutorService executorService;

    public MainThreadPoolExecutor(String threadName) {
        executorService = Executors.newSingleThreadExecutor((r) -> {
            // 创建线程并起个名字
            Thread t = new Thread(r);
            t.setName(threadName);
            return t;
        });
    }

    /**
     * 主线程开始执行逻辑
     *
     * @param ctx     通道上下文
     * @param cmdMsg  消息体
     * @param factory 消息处理工厂
     */
    public void process(ChannelHandlerContext ctx, GeneratedMessageV3 cmdMsg, ICmdHandlerFactory factory) {
        if (null == ctx ||
                null == cmdMsg) {
            return;
        }

        this.process(() -> {
            // 获取命令类
            final Class<?> cmdClazz = cmdMsg.getClass();
            final ICmdHandler<? extends GeneratedMessageV3> cmdHandler = factory.create(cmdClazz);

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

            cmdHandler.cmd(ctx, cast(cmdMsg));
        });
    }


    /**
     * 主线程开始执行逻辑
     *
     * @param ctx     通道上下文
     * @param cmdMsg  消息体
     * @param factory 消息处理工厂
     */
    public void process(ICusChannelHandlerContext ctx, GeneratedMessageV3 cmdMsg, ICmdHandlerFactory factory) {
        if (null == ctx ||
                null == cmdMsg) {
            return;
        }

        this.process(() -> {
            // 获取命令类
            final Class<?> cmdClazz = cmdMsg.getClass();
            final ICusCmdHandler<? extends GeneratedMessageV3> cmdHandler = factory.create_0(cmdClazz);

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

            cmdHandler.cmd(ctx, cast(cmdMsg));
        });
    }

    private <T extends GeneratedMessageV3> T cast(GeneratedMessageV3 cmdMsg) {
        @SuppressWarnings("unchecked")
        T tempObj = (T) cmdMsg;
        return tempObj;
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

    private static class SafeRunner implements Runnable {
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
