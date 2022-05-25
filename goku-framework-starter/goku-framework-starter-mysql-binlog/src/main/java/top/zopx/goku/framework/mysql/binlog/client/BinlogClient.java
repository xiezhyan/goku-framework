package top.zopx.goku.framework.mysql.binlog.client;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.mysql.binlog.properties.BootstrapBinlog;
import top.zopx.goku.framework.tools.util.string.StringUtil;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * Binlog客户端连接
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/24 22:38
 */
@Component
public class BinlogClient {

    @Resource
    private BinlogClientEventListener binlogClientEventListener;
    @Resource
    private BootstrapBinlog bootstrapBinlog;
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    private BinaryLogClient binaryLogClient;

    public void connect() {
        taskExecutor.execute(() -> {
            final BootstrapBinlog.Binlog binlog = bootstrapBinlog.getBinlog();
            binaryLogClient = new BinaryLogClient(
                    binlog.getHost(),
                    binlog.getPort(),
                    binlog.getUsername(),
                    binlog.getPassword()
            );

            if (StringUtil.isNotBlank(binlog.getBinlogName()) && !Objects.equals(binlog.getPosition(), -1L)) {
                binaryLogClient.setBinlogFilename(binlog.getBinlogName());
                binaryLogClient.setBinlogPosition(binlog.getPosition());
            }

            binaryLogClient.registerEventListener(binlogClientEventListener);

            try {
                LogHelper.getLogger(BinlogClient.class).info("connecting to mysql start");
                binaryLogClient.connect();
                LogHelper.getLogger(BinlogClient.class).info("connecting to mysql done");
            } catch (IOException ex) {
                LogHelper.getLogger(BinlogClient.class).error(ex.getMessage(), ex);
            }
        });
    }

    public void close() {
        try {
            binaryLogClient.disconnect();
        } catch (IOException ex) {
            LogHelper.getLogger(BinlogClient.class).error(ex.getMessage(), ex);
        }
    }
}
