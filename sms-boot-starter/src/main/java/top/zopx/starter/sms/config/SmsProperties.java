package top.zopx.starter.sms.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.zopx.starter.sms.conditional.AliYunSmsConditional;

/**
 * top.zopx.starter.sms.config.SmsProperties
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */
@ConfigurationProperties(prefix = SmsProperties.PREFIX)
@Configuration
@Component
@Data
public class SmsProperties {

    public static final String PREFIX = "sms";

    public AliYunSmsProperties aLiSms;


    @Data
    @Component
    @ConfigurationProperties(prefix = AliYunSmsProperties.PREFIX)
    public class AliYunSmsProperties {
        private static final String PRODUCT = "Dysmsapi"; //短信API产品名称（短信产品名固定，无需修改）
        private static final String DOMAIN = "dysmsapi.aliyuncs.com"; //短信API产品域名（接口地址固定，无需修改）

        public static final String PREFIX = SmsProperties.PREFIX + ".a-li-sms";

        private String accessKeyId;

        private String accessKeySecret;

        @Bean
        @Conditional(value = AliYunSmsConditional.class)
        public IAcsClient acsClient() {
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", PRODUCT, DOMAIN);
            return new DefaultAcsClient(profile);
        }
    }
}
