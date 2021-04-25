package top.zopx.starter.log;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import top.zopx.starter.log.listener.PublishEventListener;
import top.zopx.starter.log.properties.SquareLogProperties;
import top.zopx.starter.log.server.ApiLogServer;
import top.zopx.starter.log.server.ErrorLogServer;
import top.zopx.starter.log.util.SpringUtil;

/**
 * @author sanq.Yan
 * @date 2021/4/12
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({SquareLogProperties.class})
@EnableAspectJAutoProxy
public class LogServerAutoConfiguration {

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    public ErrorLogServer errorLogServer() {
        return new ErrorLogServer();
    }

    @Bean
    public ApiLogServer apiLogServer() {
        return new ApiLogServer();
    }

    @Bean
    public PublishEventListener publishEventListener() {
        return new PublishEventListener();
    }
}
