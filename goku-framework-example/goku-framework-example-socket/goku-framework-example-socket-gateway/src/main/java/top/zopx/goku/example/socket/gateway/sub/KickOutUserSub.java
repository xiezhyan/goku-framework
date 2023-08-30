package top.zopx.goku.example.socket.gateway.sub;

import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.socket.core.pubsub.ISubscribe;
import top.zopx.goku.framework.socket.netty.util.ChannelUtil;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class KickOutUserSub implements ISubscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(KickOutUserSub.class);

    @Override
    public void onMsg(String channel, String msg) {
        if (!Objects.equals(channel, RedisKeyEnum.KICK_OUT_USER_NOTICE.getKey()) || StringUtils.isBlank(msg)) {
            return;
        }

        KicketOutUserBO kicket = GsonUtil.getInstance().toObject(msg, KicketOutUserBO.class);
        if (GatewayApp.getServerId() == kicket.getFromGatewayId()) {
            LOGGER.warn("kicket out from location");
            return;
        }

        Channel oldChannel = ChannelUtil.removeByUserId(kicket.userId);
        if (null == oldChannel) {
            return;
        }

        // 记录警告日志
        LOGGER.warn("令用户断开连接, userId = {}", kicket.userId);

        Common.KickOutUserResponse resultMsg = Common.KickOutUserResponse.newBuilder()
                .setReason("强制断开用户连接")
                .build();

        oldChannel.writeAndFlush(resultMsg);
        try {
            oldChannel.disconnect().sync().await(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static class KicketOutUserBO implements Serializable {

        private Integer fromGatewayId;

        private Long userId;

        private Integer remoteSessionId;

        public Integer getFromGatewayId() {
            return fromGatewayId;
        }

        public void setFromGatewayId(Integer fromGatewayId) {
            this.fromGatewayId = fromGatewayId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Integer getRemoteSessionId() {
            return remoteSessionId;
        }

        public void setRemoteSessionId(Integer remoteSessionId) {
            this.remoteSessionId = remoteSessionId;
        }
    }
}
