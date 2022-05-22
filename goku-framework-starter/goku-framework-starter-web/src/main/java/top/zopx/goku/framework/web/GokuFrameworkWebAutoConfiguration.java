package top.zopx.goku.framework.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import top.zopx.goku.framework.web.configurator.GsonConfigurator;
import top.zopx.goku.framework.web.configurator.OkHttpFeignConfigurator;
import top.zopx.goku.framework.web.context.SpringContext;
import top.zopx.goku.framework.web.properties.BootstrapSkipUri;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:51
 */
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@Import({
        GsonConfigurator.class, OkHttpFeignConfigurator.class
})
public class GokuFrameworkWebAutoConfiguration {

    @Bean
    public SpringContext springContext() {
        return new SpringContext();
    }

    @Bean
    public BootstrapSkipUri bootstrapSkipUri() {
        return new BootstrapSkipUri();
    }
}
