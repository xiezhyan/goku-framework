package top.zopx.square;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.zopx.starter.activiti.annotation.EnableActivitiDashboard;
import top.zopx.starter.distribution.annotation.EnableDistribution;
import top.zopx.starter.sms.annotation.EnableSms;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableDistribution
@EnableSms
@EnableActivitiDashboard
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
