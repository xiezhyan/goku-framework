package top.zopx.goku.example.configurator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.zopx.goku.framework.http.util.i18n.CustomMessageSourceAccessor;

/**
 * SpringMVC Configurator
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/2/25 下午6:04
 */
@Configuration
@EnableWebMvc
public class MvcConfigurator implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Value("${spring.messages.basename}")
    private String baseName;

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return CustomMessageSourceAccessor.getAccessor(baseName);
    }
}
