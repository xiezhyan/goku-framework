package top.zopx.goku.framework.socket.core.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 20:07
 */
public interface IServerInfo {

    /**
     * 服务信息
     *
     * @return ServerInfo
     */
    ServerInfo get();

    class ServerInfo {

        /**
         * 服务ID
         */
        private int serverId;

        /**
         * 服务名称
         */
        private String serverName;

        /**
         * 处理逻辑
         */
        private Set<String> serverJobTypeSet;

        /**
         * 服务IP
         */
        private String serverIp;

        /**
         * 服务端口
         */
        private int serverPort;

        /**
         * websocket服务地址
         */
        private String path;

        /**
         * 负载数量
         */
        private double loadCount = 0D;

        public double getLoadCount() {
            return loadCount;
        }

        public void setLoadCount(double loadCount) {
            this.loadCount = loadCount;
        }

        public int getServerId() {
            return serverId;
        }

        public void setServerId(int serverId) {
            this.serverId = serverId;
        }

        public String getServerName() {
            return serverName;
        }

        public void setServerName(String serverName) {
            this.serverName = serverName;
        }

        public void setServerJobTypeSet(String serverJobTypeSetStr) {
            this.serverJobTypeSet = new HashSet<>(Arrays.asList(serverJobTypeSetStr.split(",")));
        }

        public Set<String> getServerJobTypeSet() {
            return serverJobTypeSet;
        }

        public String getServerIp() {
            return serverIp;
        }

        public void setServerIp(String serverIp) {
            this.serverIp = serverIp;
        }

        public int getServerPort() {
            return serverPort;
        }

        public void setServerPort(int serverPort) {
            this.serverPort = serverPort;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
