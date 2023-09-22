package top.zopx.goku.framework.support.primary.snowflake.service;

import com.google.gson.Gson;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.support.primary.snowflake.util.FileUtil;
import top.zopx.goku.framework.support.primary.core.entity.Node;
import top.zopx.goku.framework.support.primary.core.service.IRegisterNodeService;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/02/10
 */
public class RegisterNodeService implements IRegisterNodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNodeService.class);

    private static final Gson GSON = new Gson();

    private static final String ZK_ROOT_PATH = "/distributed_unique";
    private static final String ZK_PERSISTENT_PATH = ZK_ROOT_PATH + "/persistent";
    private static final String ZK_EPHEMERAL_PATH = ZK_ROOT_PATH + "/ephemeral";

    private static final String SEQ_PREFIX = "n";

    private static long lastCurrentTime;
    /**
     * 由于节点5s上报一次时间，所以如此设置
     */
    private static final long MUL_TIME = 5 * 1000L;

    private final CuratorFramework client;
    private final String serverName;

    /**
     * 定时任务
     */
    private static final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() << 1,
            runner -> {
                Thread thread = new Thread(runner, "create-ephemeral");
                thread.setDaemon(true);
                return thread;
            });

    public RegisterNodeService(CuratorFramework client, String serverName) {
        this.client = client;
        this.serverName = serverName;
    }

    @Override
    public int register(Node node) {
        // 1、从缓存文件中取到对应的seq，如果为-1，那么说明不存在，就向zk中注册持久节点
        int seq = FileUtil.INSTANCE.get(this.serverName, node.getPort());

        if (-1 == seq) {
            // 注册持久节点
            String path = createPersistentNode(node);
            LOGGER.info("path={}", path);
            // 重置seq
            seq = Integer.parseInt(path.replace(getPersistentPath(), ""));
            // 缓存文件
            FileUtil.INSTANCE.cache(this.serverName, node.getPort(), seq);
        }

        String ephemeralPath = getEphemeralNodePath(node.getIp(), node.getPort());
        LOGGER.info("ephemeralPath={}", ephemeralPath);

        // 这里为了方便，获取临时列表进行比对
        List<Long> nodeList = getEphemeralNodeList();
        // 和已经注册的时间进行比较
        checkCurrentTimeByNodeListData(nodeList);
        // 没问题 注册到临时节点
        createEphemeralNode(ephemeralPath);
        // 定时上报信息
        startScheduled(ephemeralPath);
        return seq;
    }

    /**
     * 时间校验
     *
     * @param nodeList 各节点时间数据
     */
    private void checkCurrentTimeByNodeListData(List<Long> nodeList) {
        long currentTime = System.currentTimeMillis();
        List<Long> list = nodeList.stream().filter(time -> -1L != time).toList();
        if (list.size() != nodeList.size()) {
            throw new BusException("节点数据获取出现异常", IBus.ERROR_CODE);
        }
        for (Long time : list) {
            if (Math.abs(time - currentTime) > MUL_TIME) {
                throw new BusException("服务器时间校验出现异常，当前时间和节点时间误差大", IBus.ERROR_CODE);
            }
        }
    }

    /**
     * 获取临时子节点下的时间数据
     *
     * @return 时间数据
     */
    private List<Long> getEphemeralNodeList() {
        try {
            List<String> nodeList = client.getChildren().forPath(getEphemeralPath());
            return nodeList.stream().map(path -> {
                try {
                    return Long.parseLong(
                            new String(
                                    client.getData().forPath(getEphemeralNodePath(path))
                            )
                    );
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
                return -1L;
            }).toList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    /**
     * 检查节点是否存在
     *
     * @param path 路径
     * @return 是否存在
     */
    private boolean checkExistsForPath(String path) {
        try {
            return null != client.checkExists().forPath(path);
        } catch (Exception e) {
            LOGGER.error("校验节点是否存在失败", e);
        }
        return false;
    }

    /**
     * 创建临时节点
     *
     * @param ephemeralPath 路径
     */
    private void createEphemeralNode(String ephemeralPath) {
        try {
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(ephemeralPath, (System.currentTimeMillis() + "").getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            LOGGER.error("临时节点创建失败", e);
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    /**
     * 每隔5s上报一次时间信息
     *
     * @param ephemeralPath 当前节点路径
     */
    private void startScheduled(String ephemeralPath) {
        scheduledExecutorService
                .scheduleAtFixedRate(() -> {
                    try {
                        if (System.currentTimeMillis() < lastCurrentTime) {
                            throw new BusException("时间上报出现问题，当前时间小于最后一次上报时间", IBus.ERROR_CODE);
                        }

                        client.setData()
                                .forPath(ephemeralPath, ((lastCurrentTime = System.currentTimeMillis()) + "").getBytes(StandardCharsets.UTF_8));
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * 将节点注册到zk 持久有序节点上
     *
     * @param node 节点信息
     * @return 注册路径
     */
    private String createPersistentNode(Node node) {
        try {
            return client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(getPersistentPath(), buildData(node));
        } catch (Exception e) {
            LOGGER.error("持久化节点创建失败", e);
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }

    /**
     * 存储数据
     *
     * @param node 数据信息
     * @return byte数组
     */
    private byte[] buildData(Node node) {
        String jsonStr = GSON.toJson(node);
        return jsonStr.getBytes(StandardCharsets.UTF_8);
    }

    private String getPersistentPath() {
        return MessageFormat.format("{0}/{1}/{2}", ZK_PERSISTENT_PATH, this.serverName, SEQ_PREFIX);
    }

    private String getEphemeralNodePath(String host, int port) {
        return MessageFormat.format("{0}/{1}_{2}", getEphemeralPath(), host, String.valueOf(port));
    }

    private String getEphemeralPath() {
        return MessageFormat.format("{0}/{1}", ZK_EPHEMERAL_PATH, this.serverName);
    }

    private String getEphemeralNodePath(String path) {
        return MessageFormat.format("{0}/{1}", getEphemeralPath(), path);
    }
}
