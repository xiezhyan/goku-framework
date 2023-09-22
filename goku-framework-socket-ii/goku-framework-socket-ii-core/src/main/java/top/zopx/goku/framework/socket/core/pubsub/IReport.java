package top.zopx.goku.framework.socket.core.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.core.entity.ReportServerInfo;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/26 19:42
 */
public interface IReport {

    Logger LOGGER = LoggerFactory.getLogger(IReport.class);


    /**
     * 发布服务上线通知
     *
     * @param serverInfo 服务
     */
    void report(ReportServerInfo serverInfo);

}
