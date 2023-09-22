package top.zopx.goku.framework.oss;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:31
 */
@Component
@Configuration(proxyBeanMethods = false)
@ComponentScan("top.zopx.goku.framework.oss")
public class GokuFrameworkOSSAutoConfiguration {

}
