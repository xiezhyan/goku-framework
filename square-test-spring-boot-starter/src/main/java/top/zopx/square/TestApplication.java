package top.zopx.square;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import top.zopx.square.service.impl.DistributationServiceImpl;
import top.zopx.starter.distribution.annotation.EnableDistribution;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@SpringBootApplication
@EnableDistribution
public class TestApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(TestApplication.class, args);

        final DistributationServiceImpl bean = run.getBean(DistributationServiceImpl.class);

        for (int i = 0; i < 20; i++) {
            new Thread(bean::lock1).start();
        }
    }

}
