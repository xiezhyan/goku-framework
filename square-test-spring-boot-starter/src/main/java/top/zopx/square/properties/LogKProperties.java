package top.zopx.square.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class LogKProperties {
    public static final String PREFIX = "logk";

    /**
     * 是否持久化
     */
    private String abc = "0";

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }
}
