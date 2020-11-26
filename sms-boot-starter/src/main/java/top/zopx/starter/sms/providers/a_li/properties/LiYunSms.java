package top.zopx.starter.sms.providers.a_li.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.zopx.starter.sms.properties.BaseSms;
import top.zopx.starter.sms.properties.SmsProperties;

import java.io.Serializable;

/**
 * 阿里云短信详细配置
 *
 * @author sanq.Yan
 * @date 2020/11/23
 */
@Configuration
@Component
@ConfigurationProperties(prefix = LiYunSms.PREFIX)
public class LiYunSms extends BaseSms implements Serializable {

    public static final String PREFIX = SmsProperties.PREFIX + ".sms-li";

    private String regionId = "cn-hangzhou";

    /**
     * 私钥
     */
    private String accessKeyId;

    /**
     * 私钥
     */
    private String accessSecret;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }
}
