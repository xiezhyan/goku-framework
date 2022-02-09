package top.zopx.square;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.zopx.starter.activiti.annotation.EnableActivitiSDK;
import top.zopx.starter.netty.EnableNettyServer;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableNettyServer
@EnableActivitiSDK
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
