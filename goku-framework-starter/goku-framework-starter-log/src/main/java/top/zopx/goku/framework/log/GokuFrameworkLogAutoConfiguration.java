package top.zopx.goku.framework.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import top.zopx.goku.framework.log.configurator.aspect.ApiLogAspect;
import top.zopx.goku.framework.log.event.listener.PublishEventListener;
import top.zopx.goku.framework.log.properties.BootstrapLog;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:31
 */
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@EnableConfigurationProperties({
        BootstrapLog.class
})
public class GokuFrameworkLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApiLogAspect apiLogAspect() {
        return new ApiLogAspect();
    }

    @Bean
    @ConditionalOnProperty(prefix = BootstrapLog.LOG_PROPERTIES, name = "lasting", havingValue = "true")
    public PublishEventListener publishEventListener() {
        return new PublishEventListener();
    }

}
