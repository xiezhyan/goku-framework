package top.zopx.goku.framework.socket.discovery.pubsub;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import top.zopx.goku.framework.socket.tools.circuit.Context;
import top.zopx.goku.framework.socket.core.cmd.IChannelHandle;
import top.zopx.goku.framework.socket.core.entity.ReportServerInfo;
import top.zopx.goku.framework.socket.core.pubsub.ISubscribe;
import top.zopx.goku.framework.socket.core.selector.ClientProfile;
import top.zopx.goku.framework.socket.core.selector.Selector;
import top.zopx.goku.framework.socket.core.selector.connect.MultiServer;
import top.zopx.goku.framework.socket.core.selector.connect.MultiServerRunner;
import top.zopx.goku.framework.socket.discovery.Redis;
import top.zopx.goku.framework.socket.discovery.constant.RedisKeyEnum;
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

    private final IChannelHandle channelHandle;
    private final String[] args;

    public ServerConnectSub(IChannelHandle channelHandle, String... args) {
        this.channelHandle = channelHandle;
        this.args = args;
    }

    @Override
    public void onMsg(String channel, String msg) {
        if (!Objects.equals(channel, RedisKeyEnum.REGISTER_SERVER.getKey()) || StringUtils.isBlank(msg)) {
            return;
        }

        Integer bizServerId = Integer.parseInt(msg);
        if (Objects.equals(bizServerId, Context.getServerId())) {
            LOG.debug("与自己相同，不需要连接, bizServerId = {}", bizServerId);
            return;
        }

        LOG.debug("发现新服务连入, biz_server_id = {}", bizServerId);

        try (Jedis jedis = Redis.getMain()) {
            // 获取服务器信息
            String key = RedisKeyEnum.KEY_SERVER_X_PREFIX.format(bizServerId);
            String serverInfoStr = jedis.get(key);
            
            if (StringUtils.isBlank(serverInfoStr)) {
                LOG.error("未查询到服务的基本信息， serverId = {}", bizServerId);
                return;
            }

            ReportServerInfo serverInfoObj = GsonUtil.getInstance()
                    .toObject(serverInfoStr, ReportServerInfo.class);

            ClientProfile profile = Selector.getInstance().getInstanceById(bizServerId);
            if (null == profile) {
                // 初始化
                Selector.getInstance().putInstance(bizServerId, new ClientProfile());
                profile = Selector.getInstance().getInstanceById(bizServerId);
            }
            profile.setLoadCount(serverInfoObj.getLoadCount());

            if (null == profile.getRunner() || !profile.getRunner().getReady()) {
                profile.setRunner(renewClient(serverInfoObj));
            }

            if (null == profile.getRunner()) {
                LOG.error(
                        "连接服务器失败, serverId = {}",
                        bizServerId
                );
            }
        }
    }

    private MultiServerRunner renewClient(ReportServerInfo serverInfo) {
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
        ClientProfile sp = Selector.getInstance().removeById(serverId);
        if (null != sp) {
            sp.setRunner(null);
        }
    }
}
