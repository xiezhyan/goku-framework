package top.zopx.goku.example.socket.biz.sub;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.biz.pubsub.ISubscribe;
import top.zopx.goku.framework.cluster.constant.PublishCons;
import top.zopx.goku.framework.tools.util.json.JsonUtil;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class UserLogoutSub implements ISubscribe {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLogoutSub.class);

    @Override
    public void onMsg(String channel, String msg) {
        if (!Objects.equals(channel, PublishCons.USER_LOGOUT_NOTICE) || StringUtils.isBlank(msg)) {
            return;
        }

        UserLogoutSubBO userLogoutSubBO = JsonUtil.getInstance().toObject(msg, UserLogoutSubBO.class);

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
