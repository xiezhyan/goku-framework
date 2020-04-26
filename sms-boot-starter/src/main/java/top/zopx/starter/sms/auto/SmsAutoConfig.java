package top.zopx.starter.sms.auto;

import com.aliyuncs.IAcsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.sms.config.SmsProperties;
import top.zopx.starter.sms.service.SmsService;
import top.zopx.starter.sms.service.impl.AliYunSmsServiceImpl;

/**
 * top.zopx.starter.sms.auto.SmsAutoConfig
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfig {

    @Bean
    @ConditionalOnClass(IAcsClient.class)
    public SmsService aliYunSmsService() {
        return new AliYunSmsServiceImpl();
    }

}
