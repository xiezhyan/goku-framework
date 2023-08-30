package top.zopx.goku.framework.support.mysql.binlog.client;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.support.mysql.binlog.properties.BootstrapBinlog;

import java.io.IOException;
import java.util.Objects;

/**
 * Binlog客户端连接
 *
 * @author Mr.Xie
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

    private static final Logger LOGGER = LoggerFactory.getLogger(BinlogClient.class);

    public void connect() {
        taskExecutor.execute(() -> {
            final BootstrapBinlog.Binlog binlog = bootstrapBinlog.getBinlog();
            binaryLogClient = new BinaryLogClient(
                    binlog.getHost(),
                    binlog.getPort(),
                    binlog.getUsername(),
                    binlog.getPassword()
            );

            // serverId
            binaryLogClient.setServerId(binlog.getServerId());

            if (StringUtils.isNotBlank(binlog.getBinlogName())) {
                binaryLogClient.setBinlogFilename(binlog.getBinlogName());
            }
            if (!Objects.equals(binlog.getPosition(), -1L)) {
                binaryLogClient.setBinlogPosition(binlog.getPosition());
            }

            binaryLogClient.registerEventListener(binlogClientEventListener);

            try {
                LOGGER.debug("connecting to mysql start");
                binaryLogClient.connect();
                LOGGER.debug("connecting to mysql done");
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        });
    }

    public void close() {
        try {
            binaryLogClient.disconnect();
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
