package top.zopx.goku.example.socket.gateway.sub;

import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.util.ChannelUtil;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.framework.biz.pubsub.ISubscribe;
import top.zopx.goku.framework.biz.constant.PublishEnum;
import top.zopx.goku.framework.tools.util.json.JsonUtil;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class KickOutUserSub implements ISubscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(KickOutUserSub.class);

    @Override
    public void onMsg(String channel, String msg) {
        if (!Objects.equals(channel, PublishEnum.KICK_OUT_USER_NOTICE) || StringUtils.isBlank(msg)) {
            return;
        }

        KicketOutUserBO kicket = JsonUtil.getInstance().toObject(msg, KicketOutUserBO.class);
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
