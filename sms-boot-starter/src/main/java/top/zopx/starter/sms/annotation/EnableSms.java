package top.zopx.starter.sms.annotation;

import org.springframework.context.annotation.Import;
import top.zopx.starter.sms.auto.SmsAutoConfig;

import java.lang.annotation.*;

/**
 * top.zopx.starter.sms.annotation.EnableSms
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SmsAutoConfig.class})
public @interface EnableSms {
}
