package top.zopx.starter.step.up.auto;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.step.up.config.StepUpProperties;
import top.zopx.starter.step.up.service.StepupService;

/**
 * top.zopx.starter.step.up.auto.StepCodeAutoConfig
 *
 * @author sanq.Yan
 * @date 2020/4/28
 */
@Configuration
@EnableConfigurationProperties(StepUpProperties.class)
public class StepCodeAutoConfig {

    @Bean(initMethod = "init")
    public StepupService stepupService () {
        return new StepupService();
    }
}
