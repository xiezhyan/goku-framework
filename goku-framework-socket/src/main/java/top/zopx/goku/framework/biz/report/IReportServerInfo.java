package top.zopx.goku.framework.biz.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.cluster.entity.IServerInfo;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 20:07
 */
public interface IReportServerInfo {

    Logger LOGGER = LoggerFactory.getLogger(IReportServerInfo.class);


    /**
     * 发布服务上线通知
     *
     * @param serverInfo 服务
     */
    void report(IServerInfo.ServerInfo serverInfo);

}
