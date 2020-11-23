package top.zopx.starter.sms.properties;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author sanq.Yan
 * @date 2020/11/23
 */
@Configuration
@Component
@ConfigurationProperties(prefix = SmsProperties.PREFIX)
public class SmsProperties {
    static final String PREFIX = "sms";

    /**
     * 阿里云短信配置
     */
    private ALiYunSms smsLi;

    public ALiYunSms getSmsLi() {
        return smsLi;
    }

    public void setSmsLi(ALiYunSms smsLi) {
        this.smsLi = smsLi;
    }


    @Bean
    @ConditionalOnProperty(prefix = ALiYunSms.PREFIX, name = {"open"}, havingValue = "true")
    public IAcsClient acsClient() {
        DefaultProfile profile = DefaultProfile.getProfile(
                smsLi.getRegionId(),
                smsLi.getAccessKeyId(),
                smsLi.getAccessSecret()
        );
        return new DefaultAcsClient(profile);
    }
}
