package top.zopx.square;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.zopx.starter.distribution.annotation.EnableDistribution;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@SpringBootApplication
@EnableDistribution
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
