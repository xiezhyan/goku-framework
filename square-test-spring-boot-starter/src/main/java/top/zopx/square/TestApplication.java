package top.zopx.square;

import com.google.gson.Gson;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import top.zopx.starter.activiti.annotation.EnableActivitiUI;
import top.zopx.starter.netty.EnableNettyCore;
import top.zopx.starter.tools.tools.json.IJson;
import top.zopx.starter.tools.tools.json.impl.GJson;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableNettyCore
@EnableActivitiUI
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public IJson json() {
        return new GJson(new Gson());
    }

}
