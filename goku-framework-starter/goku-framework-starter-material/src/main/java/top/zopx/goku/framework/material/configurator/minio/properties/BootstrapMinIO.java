package top.zopx.goku.framework.material.configurator.minio.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static top.zopx.goku.framework.material.configurator.minio.properties.BootstrapMinIO.PREFIX;
/**
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/30
 */
@Configuration
@Component
@ConfigurationProperties(value = PREFIX)
@SuppressWarnings("all")
public class BootstrapMinIO {

    public static final String PREFIX = "goku.material.minio";

    /**
     * URL，域名，IPv4或者IPv6地址
     */
    private String endpoint;

    /**
     * TCP/IP端口号
     */
    private Integer port;
    /**
     * appId
     */
    private String appSecretId;

    /**
     * appKey
     */
    private String appSecretKey;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAppSecretId() {
        return appSecretId;
    }

    public void setAppSecretId(String appSecretId) {
        this.appSecretId = appSecretId;
    }

    public String getAppSecretKey() {
        return appSecretKey;
    }

    public void setAppSecretKey(String appSecretKey) {
        this.appSecretKey = appSecretKey;
    }

}
