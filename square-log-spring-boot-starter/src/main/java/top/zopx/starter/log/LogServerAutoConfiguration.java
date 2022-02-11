package top.zopx.starter.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
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
@Import({ExceptionAdvice.class, ApiLogAspect.class})
public class LogServerAutoConfiguration {

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    public SquareLogProperties squareLogProperties() {
        return new SquareLogProperties();
    }

    @Bean
    @ConditionalOnProperty(prefix = PropertiesCons.Log.LOG_PROPERTIES, name = "endurance", havingValue = "true")
    public PublishEventListener publishEventListener() {
        return new PublishEventListener();
    }
}
