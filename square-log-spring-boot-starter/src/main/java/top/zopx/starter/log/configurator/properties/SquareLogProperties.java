package top.zopx.starter.log.configurator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.tools.constants.PropertiesCons;

/**
 * @author 俗世游子
 * @date 2021/4/14
 */
@Configuration
@ConfigurationProperties(prefix = PropertiesCons.Log.LOG_PROPERTIES)
public class SquareLogProperties {
    /**
     * 是否持久化
     */
    private Boolean lasting = false;

    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Boolean isLasting() {
        return lasting;
    }

    public void setLasting(Boolean lasting) {
        this.lasting = lasting;
    }
}
