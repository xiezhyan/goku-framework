package top.zopx.square;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import top.zopx.square.service.impl.DistributationServiceImpl;
import top.zopx.starter.activiti.annotation.EnableActiviti;
import top.zopx.starter.distribution.annotation.EnableDistribution;
import top.zopx.starter.sms.annotation.EnableSms;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableActiviti
@EnableDistribution
@EnableSms
public class TestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TestApplication.class, args);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                DistributationServiceImpl distributationService = run.getBean(DistributationServiceImpl.class);
//                distributationService.lock2("name-" + finalI);
                distributationService.lock1();
            }).start();
        }
    }

}
