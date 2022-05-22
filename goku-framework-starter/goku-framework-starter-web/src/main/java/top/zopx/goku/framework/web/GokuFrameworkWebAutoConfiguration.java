package top.zopx.goku.framework.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:51
 */
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@ComponentScan("top.zopx.goku.framework.web")
public class GokuFrameworkWebAutoConfiguration {
}
