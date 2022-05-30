package top.zopx.goku.framework.material.configurator.oss.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static top.zopx.goku.framework.material.configurator.oss.properties.BootstrapOSS.PREFIX;

/**
 * 文件配置信息
 *
 * @author 俗世游子
 * @date 2022/05/06
 * @email xiezhyan@126.com
 */
@SuppressWarnings("all")
@Configuration
@Component
@ConfigurationProperties(value = PREFIX)
public class BootstrapOSS {

    public static final String PREFIX = "goku.material.oss";

    /**
     * 存储区域
     */
    private String endpoint;
    /**
     * appId
     */
    private String appSecretId;

    /**
     * appKey
     */
    private String appSecretKey;

    /**
     * 是否支持Https
     */
    private Boolean supportHttps;

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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Boolean getSupportHttps() {
        return supportHttps;
    }

    public void setSupportHttps(Boolean supportHttps) {
        this.supportHttps = supportHttps;
    }
}