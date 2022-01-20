package top.zopx.starter.log.configurator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@Component
@Configuration
@ConfigurationProperties(prefix = SquareLogProperties.PREFIX)
public class SquareLogProperties {
    public static final String PREFIX = "square.log";

    /**
     * 是否持久化
     */
    private boolean endurance = false;

    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isEndurance() {
        return endurance;
    }

    public void setEndurance(boolean endurance) {
        this.endurance = endurance;
    }
}
