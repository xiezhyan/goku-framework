package top.zopx.goku.example.socket.gateway.sub;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import top.zopx.goku.example.socket.common.constant.Constant;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.framework.biz.pubsub.ISubscribe;
import top.zopx.goku.framework.biz.redis.RedisCache;
import top.zopx.goku.framework.cluster.constant.PublishCons;
import top.zopx.goku.framework.cluster.constant.RedisKeyCons;
import top.zopx.goku.framework.tools.util.json.JsonUtil;
import top.zopx.goku.framework.util.ChannelUtil;

import java.util.Objects;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/22 22:34
 */
public class DisconnectDuplicateLoginSub implements ISubscribe {
    private static final Logger LOGGER = LoggerFactory.getLogger(KickOutUserSub.class);

    @Override
    public void onMsg(String channel, String msg) {
        if (!Objects.equals(channel, PublishCons.DISCONNECT_DUPLICATE_LOGIN) || StringUtils.isBlank(msg)) {
            return;
        }

        final JsonObject jsonObj = JsonUtil.getInstance().toObject(msg, JsonObject.class);
        Integer gatewayId = jsonObj.get("gatewayServerId").getAsInt();
        if (!Objects.equals(gatewayId, GatewayApp.getServerId())) {
            return;
        }
        Long userId = jsonObj.get("userId").getAsLong();
        LOGGER.info(
                "收到断开重复登录通知, userId = {}",
                userId
        );

        final Channel oldChannel = ChannelUtil.getChannelByUserId(userId);
        if (null != oldChannel) {
            oldChannel.disconnect();
        } else {
            clearUserAtGatewayServerId(userId);
        }
    }

    /**
     * 清理用户所在网关服务器 Id
     *
     * @param userId 用户 Id
     */
    private void clearUserAtGatewayServerId(Long userId) {
        if (userId <= 0) {
            return;
        }

        try(Jedis jedis = RedisCache.getServerCache()) {
            // TODO 最好采用Lua脚本
            jedis.hdel(RedisKeyCons.KEY_USER_INFO.format(userId), Constant.USER_AT_PROXY_SERVER_ID);
            jedis.hdel(RedisKeyCons.GATEWAY_USER_LIST.format(GatewayApp.getServerId()), String.valueOf(userId));
        }
    }
}
