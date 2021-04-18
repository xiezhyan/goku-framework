package top.zopx.square;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import top.zopx.square.service.impl.DistributationServiceImpl;
import top.zopx.square.service.impl.SmsServiceImpl;
import top.zopx.starter.distribution.annotation.EnableDistribution;
import top.zopx.starter.sms.annotation.EnableSms;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@SpringBootApplication
@EnableDistribution
@EnableSms
public class TestApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(TestApplication.class, args);

        final SmsServiceImpl smsService = run.getBean(SmsServiceImpl.class);

        try {
            smsService.sendVer();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
