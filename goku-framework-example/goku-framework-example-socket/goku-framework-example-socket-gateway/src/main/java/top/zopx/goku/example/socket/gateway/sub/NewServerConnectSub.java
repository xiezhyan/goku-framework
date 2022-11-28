package top.zopx.goku.example.socket.gateway.sub;

import io.netty.channel.ChannelHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import top.zopx.goku.example.socket.common.constant.Constant;
import top.zopx.goku.example.socket.gateway.handle.ClientInnerMsgHandle;
import top.zopx.goku.framework.biz.pubsub.ISubscribe;
import top.zopx.goku.framework.biz.redis.RedisCache;
import top.zopx.goku.framework.biz.constant.PublishEnum;
import top.zopx.goku.framework.biz.constant.RedisKeyEnum;
import top.zopx.goku.framework.biz.entity.IServerInfo;
import top.zopx.goku.framework.netty.bind.entity.ConnectClient;
import top.zopx.goku.framework.netty.bind.factory.BaseChannelHandlerFactory;
import top.zopx.goku.framework.netty.server.Client;
import top.zopx.goku.framework.netty.server.ClientToClientAcceptor;
import top.zopx.goku.framework.tools.util.json.JsonUtil;
import top.zopx.goku.framework.tools.util.string.StringUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class NewServerConnectSub implements
        ISubscribe,
        BaseChannelHandlerFactory,
        ConnectClient.ICloseCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewServerConnectSub.class);

    private static final Map<Integer, Client.ServerProfile> ID_SERVER_MAP = new ConcurrentHashMap<>(64);

    private List<Client.ServerProfile> serverProfileList;

    private final AtomicLong REV = new AtomicLong(0);

    public static NewServerConnectSub getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final NewServerConnectSub INSTANCE = new NewServerConnectSub();
    }

    @Override
    public void onMsg(String channel, String msg) {
        if (!Objects.equals(channel, PublishEnum.REGISTER_SERVER) || StringUtils.isBlank(msg)) {
            return;
        }

        Integer bizServerId = StringUtil.toInteger(msg);

        LOGGER.debug("发现新服务连入, biz_server_id = {}", bizServerId);

        try (Jedis jedis = RedisCache.getServerCache()) {
            // 获取服务器信息
            String key = RedisKeyEnum.KEY_SERVER_X_PREFIX.format(bizServerId);
            String serverInfoStr = jedis.get(key);
            if (StringUtils.isBlank(serverInfoStr)) {
                LOGGER.error("未查询到服务的基本信息， serverId = {}", bizServerId);
                return;
            }

            IServerInfo.ServerInfo serverInfoObj = JsonUtil.getInstance().toObject(serverInfoStr, IServerInfo.ServerInfo.class);

            Client.ServerProfile profile = ID_SERVER_MAP.get(bizServerId);
            if (null == profile) {
                // 初始化
                ID_SERVER_MAP.put(bizServerId, new Client.ServerProfile());
                serverProfileList = null;
                profile = ID_SERVER_MAP.get(bizServerId);
            }
            profile.setLoadCount(serverInfoObj.getLoadCount());

            if (null == profile.getClient() || !profile.getClient().isReady()) {
                profile.setClient(renewClient(serverInfoObj));
            }

            if (null == profile.getClient()) {
                LOGGER.error(
                        "连接服务器失败, serverId = {}",
                        bizServerId
                );
            }
        }
    }

    public long getRev() {
        return REV.get();
    }

    private Client renewClient(IServerInfo.ServerInfo serverInfoObj) {
        Client client = new Client(
                ConnectClient.create()
                        .setServerId(serverInfoObj.getServerId())
                        .setServerHost(serverInfoObj.getServerIp())
                        .setServerPort(serverInfoObj.getServerPort())
                        .setServerName(serverInfoObj.getServerName())
                        .setServerType(ConnectClient.Constant.WS)
                        .setPath(Constant.WEBSOCKET_PATH)
                        .setServerJobTypeSet(serverInfoObj.getServerJobTypeSet())
                        .setChannelHandlerFactory(this)
                        .setCloseCallback(this)
                        .build(),
                serverInfoObj.getServerId()
        );
        client.connect();
        // 设置版本号
        REV.set(System.currentTimeMillis());
        if (client.isReady()) {
            return client;
        }
        return null;
    }

    @Override
    public ChannelHandler createWebsocketMsgHandler() {
        return new ClientInnerMsgHandle();
    }

    @Override
    public void apply(ClientToClientAcceptor closeClient) {
        if (null == closeClient) {
            return;
        }

        int serverId = closeClient.getServerId();
        // 如果断线就删除
        Client.ServerProfile sp = ID_SERVER_MAP.remove(serverId);
        serverProfileList = null;
        if (null != sp) {
            sp.setClient(null);
        }
    }

    public List<Client.ServerProfile> getServerProfileList() {
        if (null == serverProfileList) {
            serverProfileList = new ArrayList<>(ID_SERVER_MAP.values());
            serverProfileList.sort(Comparator.comparing(Client.ServerProfile::getServerId));
        }
        return serverProfileList;
    }

    public Client.ServerProfile getServerProfileById(int serverId) {
        return ID_SERVER_MAP.get(serverId);
    }
}
