package top.zopx.starter.log.configurator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.tools.constants.PropertiesCons;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@Configuration
@ConfigurationProperties(prefix = PropertiesCons.Log.LOG_PROPERTIES)
public class SquareLogProperties {
    /**
     * 是否持久化
     */
    private Boolean endurance = false;

    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Boolean getEndurance() {
        return endurance;
    }

    public void setEndurance(Boolean endurance) {
        this.endurance = endurance;
    }
}
