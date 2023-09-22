package top.zopx.goku.example.socket.biz.sub;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.core.pubsub.ISubscribe;
import top.zopx.goku.framework.socket.redis.constant.RedisKeyEnum;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class UserLogoutSub implements ISubscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLogoutSub.class);

    @Override
    public void onMsg(String channel, String msg) {
        if (!Objects.equals(channel, RedisKeyEnum.USER_LOGOUT_NOTICE.getKey()) || StringUtils.isBlank(msg)) {
            return;
        }

        UserLogoutSubBO userLogoutSubBO = GsonUtil.getInstance().toObject(msg, UserLogoutSubBO.class);

        LOGGER.info(
                "收到用户离线通知, remoteSessionId = {}, userId = {}",
                userLogoutSubBO.getRemoteSessionId(),
                userLogoutSubBO.getUserId()
        );
    }

    public static class UserLogoutSubBO implements Serializable {
        private Integer gatewayServerId;
        private Integer remoteSessionId;
        private Long userId;

        public Integer getGatewayServerId() {
            return gatewayServerId;
        }

        public void setGatewayServerId(Integer gatewayServerId) {
            this.gatewayServerId = gatewayServerId;
        }

        public Integer getRemoteSessionId() {
            return remoteSessionId;
        }

        public void setRemoteSessionId(Integer remoteSessionId) {
            this.remoteSessionId = remoteSessionId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}
