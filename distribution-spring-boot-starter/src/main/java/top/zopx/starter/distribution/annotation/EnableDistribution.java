package top.zopx.starter.distribution.annotation;

import org.springframework.context.annotation.Import;
import top.zopx.starter.distribution.server.DistributionMarkerConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启注解
 * @author sanq.Yan
 * @date 2021/3/28
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Import(DistributionMarkerConfiguration.class)
public @interface EnableDistribution {
}
