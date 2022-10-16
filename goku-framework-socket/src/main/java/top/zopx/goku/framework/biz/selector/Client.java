package top.zopx.goku.framework.biz.selector;

import top.zopx.goku.framework.netty.bind.entity.ConnectClient;
import top.zopx.goku.framework.netty.server.GatewayToBizServerAcceptor;

import java.util.Collections;
import java.util.Set;

/**
 * 业务服务器处理
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public class Client {

    /**
     * 是否已经连接成功
     */
    private boolean isReady = false;

    /**
     * 客户端
     */
    private final GatewayToBizServerAcceptor acceptor;

    private final int clientId;

    private final String clientName;

    public Client(ConnectClient connectClient, int serverId) {
        acceptor = new GatewayToBizServerAcceptor(connectClient);
        acceptor.putExtraInfo("gatewayId", String.valueOf(serverId));
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
        private Client client;

        /**
         * 负载数量
         */
        private int loadCount;

        public int getServerId() {
            return client.getClientId();
        }

        public Client getClient() {
            return client;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        public int getLoadCount() {
            return loadCount;
        }

        public void setLoadCount(int loadCount) {
            this.loadCount = loadCount;
        }

        public Set<String> getServerJobTypeSet() {
            if (null == client) {
                return Collections.emptySet();
            } else {
                return client.getServerJobTypeSet();
            }
        }
    }
}
