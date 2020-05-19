package top.zopx.starter.sms.auto;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.sms.conditional.AliYunSmsConditional;
import top.zopx.starter.sms.conditional.EmailConditional;
import top.zopx.starter.sms.config.SmsProperties;
import top.zopx.starter.sms.service.MailService;
import top.zopx.starter.sms.service.SmsService;
import top.zopx.starter.sms.service.impl.AliYunSmsServiceImpl;
import top.zopx.starter.sms.service.impl.MailServiceImpl;

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
    @Conditional(value = AliYunSmsConditional.class)
    public SmsService aliYunSmsService() {
        return new AliYunSmsServiceImpl();
    }

    @Bean
    @Conditional(value = EmailConditional.class)
    public MailService mailService() {
        return new MailServiceImpl();
    }
}
