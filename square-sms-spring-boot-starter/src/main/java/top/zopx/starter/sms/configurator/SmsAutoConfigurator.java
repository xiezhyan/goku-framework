package top.zopx.starter.sms.configurator;

import com.aliyuncs.IAcsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.sms.properties.SquareSmsProperties;
import top.zopx.starter.sms.service.ISmsService;
import top.zopx.starter.sms.providers.cloud.service.impl.CloudSmsServiceImpl;

/**
 * 自动配置
 * @author sanq.Yan
 * @date 2020/11/23
 */
@Configuration
@EnableConfigurationProperties({SquareSmsProperties.class})
public class SmsAutoConfigurator {

    @Bean
    @ConditionalOnClass(IAcsClient.class)
    public ISmsService aliyunSmsService() {
        return new CloudSmsServiceImpl();
    }
}
