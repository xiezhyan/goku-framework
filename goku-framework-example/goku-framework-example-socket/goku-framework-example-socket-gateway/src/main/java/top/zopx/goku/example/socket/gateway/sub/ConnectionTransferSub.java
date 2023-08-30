package top.zopx.goku.example.socket.gateway.sub;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.example.socket.gateway.GatewayApp;
import top.zopx.goku.example.socket.gateway.handle.ClientMsgHandleMsg;
import top.zopx.goku.example.socket.proto.common.Common;
import top.zopx.goku.framework.socket.core.pubsub.ISubscribe;
import top.zopx.goku.framework.socket.netty.util.ChannelUtil;
import top.zopx.goku.framework.socket.netty.util.IdUtil;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class ConnectionTransferSub implements ISubscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionTransferSub.class);

    @Override
    public void onMsg(String channel, String msg) {
        if (!Objects.equals(channel, RedisKeyEnum.CONNECTION_TRANSFER_NOTICE.getKey()) || StringUtils.isBlank(msg)) {
            return;
        }

        JsonObject jsonObject = GsonUtil.getInstance().toObject(msg, JsonObject.class);

        Integer newGatewayId = jsonObject.get("newGatewayId").getAsInt();

        if (Objects.equals(newGatewayId, GatewayApp.getServerId())) {
            // 转移一致，不处理
            return;
        }
        Long userId = jsonObject.get("userId").getAsLong();

        Channel oldChannel = ChannelUtil.removeByUserId(userId);

        if (null != oldChannel) {
            LOGGER.info(
                    "客户端断开连接, sessionId = {}, userId = {}",
                    IdUtil.getSessionId(oldChannel),
                    userId
            );

            try {
                ClientMsgHandleMsg msgHandler = oldChannel.pipeline().get(ClientMsgHandleMsg.class);
                msgHandler.setConnAlreadyTransfer(true);

                Common.KickOutUserResponse response = Common.KickOutUserResponse.newBuilder()
                        .setReason("成功连接到其他服务器")
                        .build();

                oldChannel.writeAndFlush(response);
                oldChannel.disconnect().sync().await(2, TimeUnit.SECONDS);
            } catch (Exception ex) {
                // 记录错误日志
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }
}
