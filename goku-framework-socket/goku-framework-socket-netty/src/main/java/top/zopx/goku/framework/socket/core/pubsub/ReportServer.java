package top.zopx.goku.framework.socket.core.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.core.entity.IServerInfo;
import top.zopx.goku.framework.socket.core.util.Timer;

import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 20:07
 */
public record ReportServer(Config config) {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServer.class);

    public void start() {
        Timer.start(this::startReportCurrServSync, 2000, config.getReportInterval(), TimeUnit.MILLISECONDS);
    }

    private void startReportCurrServSync() {
        // 获取回调实现
        final IServerInfo callbackImpl = config.getServerInfo();

        if (null == callbackImpl) {
            // 如果没有设置回调函数,
            LOGGER.error("未设置 '获取服务器信息' 的回调函数");
            return;
        }

        // 获取服务器信息
        IServerInfo.ServerInfo newInfo = callbackImpl.get();

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

        IServerInfo serverInfo;

        IReport reportServerInfo;

        public int getReportInterval() {
            return reportInterval;
        }

        public Config setReportInterval(int reportInterval) {
            this.reportInterval = reportInterval;
            return this;
        }

        public IServerInfo getServerInfo() {
            return serverInfo;
        }

        public Config setServerInfo(IServerInfo serverInfo) {
            this.serverInfo = serverInfo;
            return this;
        }

        public IReport getReportServerInfo() {
            return reportServerInfo;
        }

        public Config setReportServerInfo(IReport reportServerInfo) {
            this.reportServerInfo = reportServerInfo;
            return this;
        }
    }

}
