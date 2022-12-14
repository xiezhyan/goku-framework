package top.zopx.goku.framework.log.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static top.zopx.goku.framework.log.properties.BootstrapLog.LOG_PROPERTIES;

/**
 * @author 俗世游子
 * @date 2021/4/14
 */
@Configuration
@ConfigurationProperties(value = LOG_PROPERTIES)
public class BootstrapLog {
    public static final String LOG_PROPERTIES = "goku.log";
    /**
     * 是否持久化
     */
    private Boolean lasting = false;

    /**
     * 服务名称
     */
    private String appName;

    /**
     * 日志级别
     */
    private String level = "debug";

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
