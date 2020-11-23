package top.zopx.starter.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.zopx.starter.sms.annotation.EnableSms;

/**
 * @author sanq.Yan
 * @date 2020/11/23
 */
@SpringBootApplication(scanBasePackages = {
        "top.zopx.starter.tools",
        "top.zopx.starter.web"
})
@EnableSms
public class SquareWebBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(SquareWebBootstrap.class, args);
    }
}
