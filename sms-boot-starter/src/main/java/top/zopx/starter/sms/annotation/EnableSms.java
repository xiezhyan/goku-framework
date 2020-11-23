package top.zopx.starter.sms.annotation;

import org.springframework.context.annotation.Import;
import top.zopx.starter.sms.configurator.SmsAutoConfigurator;

import java.lang.annotation.*;

/**
 * 开启短信服务
 * @author sanq.Yan
 * @date 2020/11/23
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Import({SmsAutoConfigurator.class})
@Documented
public @interface EnableSms {
}
