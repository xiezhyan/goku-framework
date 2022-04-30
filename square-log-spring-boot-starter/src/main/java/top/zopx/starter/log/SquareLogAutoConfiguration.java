package top.zopx.starter.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import top.zopx.starter.log.configurator.mvc.advice.ExceptionAdvice;
import top.zopx.starter.log.configurator.mvc.aspect.ApiLogAspect;
import top.zopx.starter.log.configurator.properties.SquareLogProperties;
import top.zopx.starter.log.event.listener.PublishEventListener;
import top.zopx.starter.log.util.SpringUtil;
import top.zopx.starter.tools.constants.PropertiesCons;

/**
 * @author sanq.Yan
 * @date 2021/4/12
 */
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@EnableConfigurationProperties({
        SquareLogProperties.class
})
public class SquareLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApiLogAspect apiLogAspect() {
        return new ApiLogAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionAdvice exceptionAdvice() {
        return new ExceptionAdvice();
    }

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    @ConditionalOnProperty(prefix = PropertiesCons.Log.LOG_PROPERTIES, name = "lasting", havingValue = "true")
    public PublishEventListener publishEventListener() {
        return new PublishEventListener();
    }
}
