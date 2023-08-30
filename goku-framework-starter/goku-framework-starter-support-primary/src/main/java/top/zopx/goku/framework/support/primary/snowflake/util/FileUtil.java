package top.zopx.goku.framework.support.primary.snowflake.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/02/10
 */
public enum FileUtil {
    /**
     * 单例模式
     */
    INSTANCE,
    ;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    private static final String ROOT_PAHT = System.getProperty("java.io.tmpdir");
    private static final String UNIQUE_PATH = "distributed_unique";

    /**
     * 将有序节点缓存到本地，方便查询
     *
     * @param serverName 服务名
     * @param port       端口
     * @param seq        序号
     */
    public void cache(String serverName, int port, int seq) {
        File file = getFile(serverName, port);
        try {
            FileUtils.write(
                    file,
                    "seq=" + seq,
                    Charset.defaultCharset()
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 获取到本地缓存的序号
     *
     * @param serverName 服务名
     * @param port       端口
     * @return
     */
    public int get(String serverName, int port) {
        File file = getFile(serverName, port);
        try {
            String msg = FileUtils.readFileToString(file, Charset.defaultCharset());
            if (msg.contains("=")) {
                return Integer.parseInt(msg.split("=")[1]);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return -1;
    }

    /**
     * 获取到缓存文件
     *
     * @param serverName 服务名
     * @param port       端口
     * @return File
     */
    private File getFile(String serverName, int port) {
        // C:\Users\CHINAS~1\AppData\Local\Temp\shopping-server\16675\shopping-server
        String path = ROOT_PAHT + File.separator + UNIQUE_PATH + File.separator + serverName + File.separator + port + File.separator + serverName;
        LOGGER.debug("file path={}", path);
        return new File(path);
    }
}
