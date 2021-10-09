package top.zopx.netty.annotation;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import top.zopx.netty.configurator.NettyProperties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 俗世游子
 * @date 2021/10/3
 * @email xiezhyan@126.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableConfigurationProperties({
        NettyProperties.class
})
public @interface EnableNettyServer {
}
