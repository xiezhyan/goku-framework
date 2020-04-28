package top.zopx.starter.step.up.auto;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zopx.starter.step.up.config.StepUpProperties;
import top.zopx.starter.step.up.dao.TableDataDao;
import top.zopx.starter.step.up.service.StepupService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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
    public TableDataDao tableDataDao () {
        return new TableDataDao();
    }

    @Bean(initMethod = "init")
    @ConditionalOnClass(TableDataDao.class)
    public StepupService stepupService () {
        return new StepupService();
    }
}
