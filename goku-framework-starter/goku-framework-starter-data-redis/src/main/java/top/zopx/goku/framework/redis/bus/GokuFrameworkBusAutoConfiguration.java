package top.zopx.goku.framework.redis.bus;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/23 7:38
 */
@Component
@Configuration(proxyBeanMethods = false)
@ComponentScan("top.zopx.goku.framework.redis.bus")
public class GokuFrameworkBusAutoConfiguration {
}
