package top.zopx.goku.framework.socket.core.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.core.entity.ReportServerInfo;
import top.zopx.goku.framework.socket.core.util.Timer;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 20:07
 */
public record ReportServer(Config config) {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServer.class);

    public void start() {
        Timer.start(
                this::startReportCurrServSync,
                2000,
                config.getReportInterval(),
                TimeUnit.MILLISECONDS
        );
    }

    private void startReportCurrServSync() {
        // 获取回调实现
        Supplier<ReportServerInfo> callbackImpl = config.getServerInfo();
        if (null == callbackImpl) {
            LOGGER.error("未设置 上报服务 回调函数 ");
            return;
        }

        ReportServerInfo newInfo = callbackImpl.get();
        if (null == newInfo) {
            LOGGER.error("服务器信息为空");
            return;
        }

        // 默认采用redis发布，可配置
        config.getReportServerInfo().report(newInfo);
    }

    public static class Config {
        /**
         * 延迟时间
         */
        private int reportInterval = 5000;

        Supplier<ReportServerInfo> serverInfo;

        IReport reportServerInfo;

        public int getReportInterval() {
            return reportInterval;
        }

        public Config setReportInterval(int reportInterval) {
            this.reportInterval = reportInterval;
            return this;
        }

        public IReport getReportServerInfo() {
            return reportServerInfo;
        }

        public Config setReportServerInfo(IReport reportServerInfo) {
            this.reportServerInfo = reportServerInfo;
            return this;
        }

        public Supplier<ReportServerInfo> getServerInfo() {
            return serverInfo;
        }

        public Config setServerInfo(Supplier<ReportServerInfo> serverInfo) {
            this.serverInfo = serverInfo;
            return this;
        }
    }

}
