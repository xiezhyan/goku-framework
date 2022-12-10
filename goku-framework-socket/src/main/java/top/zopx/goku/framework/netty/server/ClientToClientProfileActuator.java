package top.zopx.goku.framework.netty.server;

import top.zopx.goku.framework.netty.bind.entity.ConnectClient;

import java.util.Collections;
import java.util.Set;

/**
 * 业务服务器处理
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public class ClientToClientProfileActuator {

    public static final String GATEWAY_ID = "GatewayId";

    public static final String CLIENT_ID = "ClientId";

    /**
     * 是否已经连接成功
     */
    private boolean isReady = false;

    /**
     * 客户端
     */
    private final ClientToClientActuator acceptor;

    private final int clientId;

    private final String clientName;

    /**
     * Client To Client
     *
     * @param connectClient 参数配置信息
     * @param extraInfoArr  额外参数
     *                      gatewayId, 1001, name,123
     */
    public ClientToClientProfileActuator(ConnectClient connectClient, String... extraInfoArr) {
        acceptor = new ClientToClientActuator(connectClient);
        for (int i = 0, size = extraInfoArr.length; i < size; i += 2) {
            acceptor.putExtraInfo(extraInfoArr[i], extraInfoArr[i + 1]);
        }
        this.clientId = connectClient.getServerId();
        this.clientName = connectClient.getServerName();
    }

    public void connect() {
        acceptor.connect();
        isReady = acceptor.isReady();
    }

    public void sendMsg(Object msg) {
        acceptor.sendMsg(msg);
    }

    public boolean isReady() {
        return isReady;
    }

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public Set<String> getServerJobTypeSet() {
        return acceptor.getServerJobTypeSet();
    }

    public static class ServerProfile {

        /**
         * 客户端信息
         */
        private ClientToClientProfileActuator clientToClientProfileActuator;

        /**
         * 负载数量
         */
        private int loadCount;

        public int getServerId() {
            return clientToClientProfileActuator.getClientId();
        }

        public ClientToClientProfileActuator getClient() {
            return clientToClientProfileActuator;
        }

        public void setClient(ClientToClientProfileActuator clientToClientProfileActuator) {
            this.clientToClientProfileActuator = clientToClientProfileActuator;
        }

        public int getLoadCount() {
            return loadCount;
        }

        public void setLoadCount(int loadCount) {
            this.loadCount = loadCount;
        }

        public Set<String> getServerJobTypeSet() {
            if (null == clientToClientProfileActuator) {
                return Collections.emptySet();
            } else {
                return clientToClientProfileActuator.getServerJobTypeSet();
            }
        }
    }
}
