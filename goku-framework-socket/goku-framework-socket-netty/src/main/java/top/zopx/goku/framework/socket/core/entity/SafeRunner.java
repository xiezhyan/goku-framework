package top.zopx.goku.framework.socket.core.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/24
 */
public record SafeRunner(Runnable runnable) implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SafeRunner.class);

    @Override
    public void run() {
        if (null == runnable) {
            return;
        }

        try {
            // 运行
            runnable.run();
        } catch (Exception ex) {
            // 记录错误日志
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
