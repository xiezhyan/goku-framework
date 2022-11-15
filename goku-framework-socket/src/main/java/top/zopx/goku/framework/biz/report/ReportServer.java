package top.zopx.goku.framework.biz.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.cluster.entity.IServerInfo;
import top.zopx.goku.framework.cluster.util.Timer;

import java.util.concurrent.TimeUnit;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 20:07
 */
public class ReportServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServer.class);

    public static class Config {
        /**
         * 延迟时间
         */
        private int reportInterval = 5000;

        IServerInfo serverInfo;

        IReportServerInfo reportServerInfo;

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

        public IReportServerInfo getReportServerInfo() {
            return reportServerInfo;
        }

        public Config setReportServerInfo(IReportServerInfo reportServerInfo) {
            this.reportServerInfo = reportServerInfo;
            return this;
        }
    }

    private final Config config;

    public ReportServer(Config config) {
        this.config = config;
    }

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

}
