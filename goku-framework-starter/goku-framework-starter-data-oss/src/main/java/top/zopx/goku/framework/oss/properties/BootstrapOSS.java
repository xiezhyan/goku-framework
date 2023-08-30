package top.zopx.goku.framework.oss.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

import static top.zopx.goku.framework.oss.properties.BootstrapOSS.PREFIX;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/22
 */
@Configuration
@ConfigurationProperties(prefix = PREFIX)
public class BootstrapOSS implements Serializable {

    public static final String PREFIX = "goku.oss";

    /**
     * 连接地址
     */
    private String endPoint;

    /**
     * 区域
     */
    private String region;

    /**
     * accessKeyId
     */
    private String accessKey;

    /**
     * accessKeySecret
     */
    private String secretKey;

    /**
     * true path-style nginx 反向代理和S3默认支持 pathStyle模式 {http://endpoint/bucketname}
     * false supports virtual-hosted-style 阿里云等需要配置为 virtual-hosted-style 模式{http://bucketname.endpoint}
     * 只是url的显示不一样
     */
    private Boolean pathStyle = Boolean.TRUE;

    private BootstrapClient client = new BootstrapClient();

    public static class BootstrapClient implements Serializable{

        /**
         * 最大连接数
         */
        private Integer maxConnections = 100;

        /**
         * The default timeout for reading from a connected socket
         */
        private int socketTimeout = 5_0000;
        /**
         * The default timeout for creating new connections
         */
        private int connectionTimeout = 1_0000;
        /**
         * The default timeout for a request. This is disabled by default.
         */
        private int requestTimeout = 0;

        /**
         * 连接池连接时间
         */
        private long connectionTTL = -1;

        public long getConnectionTTL() {
            return connectionTTL;
        }

        public void setConnectionTTL(long connectionTTL) {
            this.connectionTTL = connectionTTL;
        }

        public Integer getMaxConnections() {
            return maxConnections;
        }

        public void setMaxConnections(Integer maxConnections) {
            this.maxConnections = maxConnections;
        }

        public int getSocketTimeout() {
            return socketTimeout;
        }

        public void setSocketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public int getRequestTimeout() {
            return requestTimeout;
        }

        public void setRequestTimeout(int requestTimeout) {
            this.requestTimeout = requestTimeout;
        }
    }

    public BootstrapClient getClient() {
        return client;
    }

    public void setClient(BootstrapClient client) {
        this.client = client;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getPathStyle() {
        return pathStyle;
    }

    public void setPathStyle(Boolean pathStyle) {
        this.pathStyle = pathStyle;
    }
}
