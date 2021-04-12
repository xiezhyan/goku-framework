package top.zopx.starter.server.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sanq.Yan
 * @date 2021/4/2
 */
@Target({ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Component
@ComponentScan(basePackages = "top.zopx.starter.tools")
public @interface EnableTools {
}
