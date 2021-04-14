package top.zopx.starter.sms.properties;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.zopx.starter.sms.providers.cloud.properties.LiYunSms;

/**
 * @author sanq.Yan
 * @date 2020/11/23
 */
@Configuration
@Component
@ConfigurationProperties(prefix = SmsProperties.PREFIX)
public class SmsProperties {
    public static final String PREFIX = "square.sms";

    /**
     * 阿里云短信配置
     */
    private LiYunSms smsLi;

    public LiYunSms getSmsLi() {
        return smsLi;
    }

    public void setSmsLi(LiYunSms smsLi) {
        this.smsLi = smsLi;
    }


    @Bean
    @ConditionalOnProperty(prefix = LiYunSms.PREFIX, name = {"open"}, havingValue = "true")
    public IAcsClient acsClient() {
        DefaultProfile profile = DefaultProfile.getProfile(
                smsLi.getRegionId(),
                smsLi.getAccessKeyId(),
                smsLi.getAccessSecret()
        );
        return new DefaultAcsClient(profile);
    }
}
