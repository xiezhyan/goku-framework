package top.zopx.goku.example.socket.gateway.sub;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import top.zopx.goku.example.socket.common.constant.Constant;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.framework.socket.core.pubsub.ISubscribe;
import top.zopx.goku.framework.socket.netty.util.ChannelUtil;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;
import top.zopx.goku.framework.socket.redis.Redis;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.util.Objects;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/22 22:34
 */
public class DisconnectDuplicateLoginSub implements ISubscribe {
    private static final Logger LOGGER = LoggerFactory.getLogger(KickOutUserSub.class);

    @Override
    public void onMsg(String channel, String msg) {
        if (!Objects.equals(channel, RedisKeyEnum.DISCONNECT_DUPLICATE_LOGIN.getKey()) || StringUtils.isBlank(msg)) {
            return;
        }

        final JsonObject jsonObj = GsonUtil.getInstance().toObject(msg, JsonObject.class);
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

        try(Jedis jedis = Redis.get("server")) {
            // TODO 最好采用Lua脚本
            jedis.hdel(RedisKeyEnum.KEY_USER_INFO.format(userId), Constant.USER_AT_PROXY_SERVER_ID);
            jedis.hdel(RedisKeyEnum.GATEWAY_USER_LIST.format(GatewayApp.getServerId()), String.valueOf(userId));
        }
    }
}
