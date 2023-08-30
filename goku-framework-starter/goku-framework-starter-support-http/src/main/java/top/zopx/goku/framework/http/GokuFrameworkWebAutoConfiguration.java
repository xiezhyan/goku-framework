package top.zopx.goku.framework.http;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import top.zopx.goku.framework.http.configurator.record.ApiLogAspect;
import top.zopx.goku.framework.http.util.log.LogHelper;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:51
 */
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@EnableAsync
@ComponentScan("top.zopx.goku.framework.http")
public class GokuFrameworkWebAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "goku.record", name = "persist", havingValue = "true")
    public ApiLogAspect apiLogAspect() {
        return new ApiLogAspect();
    }

    @PostConstruct
    public void banner() {
        LogHelper.getLogger(getClass())
                .info(
                        "\n{}",
                        """
                                  ,--,    .---.  ,-. .-..-. .-.\s
                                .' .'    / .-. ) | |/ / | | | |\s
                                |  |  __ | | |(_)| | /  | | | |\s
                                \\  \\ ( _)| | | | | | \\  | | | |\s
                                 \\  `-) )\\ `-' / | |) \\ | `-')|\s
                                 )\\____/  )---'  |((_)-'`---(_)\s
                                (__)     (_)     (_)           \s
                                                                
                                GoKu:   2.0.05_10
                                """
                );
    }

}
