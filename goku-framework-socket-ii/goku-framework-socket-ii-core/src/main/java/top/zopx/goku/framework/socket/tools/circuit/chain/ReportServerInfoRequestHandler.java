package top.zopx.goku.framework.socket.tools.circuit.chain;

import top.zopx.goku.framework.socket.tools.circuit.Context;
import top.zopx.goku.framework.socket.core.entity.ReportServerInfo;
import top.zopx.goku.framework.socket.core.pubsub.IReport;
import top.zopx.goku.framework.socket.core.pubsub.ReportServer;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/19 18:42
 */
public class ReportServerInfoRequestHandler implements RequestHandler {

    private final IReport report;

    public ReportServerInfoRequestHandler(IReport report) {
        this.report = report;
    }

    @Override
    public void handleRequest(Context context) {
        LOG.info("。。。开始上报当前服务信息。。。");
        ReportServer.Config config =
                new ReportServer.Config()
                        .setServerInfo(() -> {
                            ReportServerInfo serverInfo = new ReportServerInfo();
                            serverInfo.setServerId(Context.getServerId());
                            serverInfo.setServerIp(Context.getServerHost());
                            serverInfo.setServerPort(Context.getServerPort());
                            serverInfo.setPath(Context.getServerPath());
                            serverInfo.setServerJobTypeSet(Context.getServerJobTypeSet());

                            serverInfo.setLoadCount(Context.getLoadCount());
                            return serverInfo;
                        })
                        .setReportServerInfo(report);

        new ReportServer(config)
                .start();

    }
}
