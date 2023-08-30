package top.zopx.goku.framework.socket.redis.selector;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import top.zopx.goku.framework.socket.core.entity.IServerInfo;
import top.zopx.goku.framework.socket.core.pubsub.ISubscribe;
import top.zopx.goku.framework.socket.netty.entity.ClientProfile;
import top.zopx.goku.framework.socket.netty.handle.IChannelHandle;
import top.zopx.goku.framework.socket.netty.properties.MultiServer;
import top.zopx.goku.framework.socket.netty.runner.MultiServerRunner;
import top.zopx.goku.framework.socket.netty.selector.SelectorUtil;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;
import top.zopx.goku.framework.socket.redis.Redis;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.util.Objects;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
@SuppressWarnings("all")
public class ServerConnectSub implements
        ISubscribe,
        MultiServer.ICloseCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConnectSub.class);

    private final IChannelHandle channelHandle;
    private final String[] args;
    private final Integer selfId;

    public ServerConnectSub(IChannelHandle channelHandle, Integer selfId, String... args) {
        this.channelHandle = channelHandle;
        this.args = args;
        this.selfId = selfId;
    }

    @Override
    public void onMsg(String channel, String msg) {
        if (!Objects.equals(channel, RedisKeyEnum.REGISTER_SERVER.getKey()) || StringUtils.isBlank(msg)) {
            return;
        }

        Integer bizServerId = Integer.parseInt(msg);

        if (Objects.equals(this.selfId, bizServerId)) {
            return;
        }

        LOGGER.debug("发现新服务连入, biz_server_id = {}", bizServerId);

        try (Jedis jedis = Redis.get()) {
            // 获取服务器信息
            String key = RedisKeyEnum.KEY_SERVER_X_PREFIX.format(bizServerId);
            String serverInfoStr = jedis.get(key);
            if (StringUtils.isBlank(serverInfoStr)) {
                LOGGER.error("未查询到服务的基本信息， serverId = {}", bizServerId);
                return;
            }

            IServerInfo.ServerInfo serverInfoObj = GsonUtil.getInstance().toObject(serverInfoStr, IServerInfo.ServerInfo.class);

            ClientProfile profile = SelectorUtil.get(bizServerId);
            if (null == profile) {
                // 初始化
                SelectorUtil.put(bizServerId, new ClientProfile());
                SelectorUtil.clean();
                profile = SelectorUtil.get(bizServerId);
            }
            profile.setLoadCount(serverInfoObj.getLoadCount());

            if (null == profile.getRunner() || !profile.getRunner().getReady()) {
                profile.setRunner(renewClient(serverInfoObj));
            }

            if (null == profile.getRunner()) {
                LOGGER.error(
                        "连接服务器失败, serverId = {}",
                        bizServerId
                );
            }
        }
    }

    private MultiServerRunner renewClient(IServerInfo.ServerInfo serverInfo) {
        MultiServerRunner serverRunner =
                new MultiServerRunner(
                        MultiServer.create()
                                .setServerId(serverInfo.getServerId())
                                .setServerName(serverInfo.getServerName())
                                .setServerHost(serverInfo.getServerIp())
                                .setServerPort(serverInfo.getServerPort())
                                .setWebsocketPath(serverInfo.getPath())
                                .setServerType(MultiServer.ServerEnum.WEB_SOCKET)
                                .setServerJobTypeSet(serverInfo.getServerJobTypeSet())
                                .setCloseCallback(this)
                                .setChannelHandle(this.channelHandle)
                                .build(),
                        args
                );

        serverRunner.connect();
        // 设置版本号
        SelectorUtil.setRev(System.currentTimeMillis());
        if (Boolean.TRUE.equals(serverRunner.getReady())) {
            return serverRunner;
        }
        return null;
    }


    @Override
    public void apply(MultiServerRunner closeClient) {
        if (null == closeClient) {
            return;
        }

        int serverId = closeClient.getServerId();
        // 如果断线就删除
        ClientProfile sp = SelectorUtil.remove(serverId);
        SelectorUtil.clean();
        if (null != sp) {
            sp.setRunner(null);
        }
    }
}
