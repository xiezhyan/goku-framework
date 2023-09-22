package top.zopx.goku.framework.support.primary.annotation;

import org.springframework.context.annotation.Import;
import top.zopx.goku.framework.support.primary.configurator.initial.MySQLInitialConfigurator;
import top.zopx.goku.framework.support.primary.configurator.marker.MySQLMarkerConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启 Zookeeper 分布式ID的支持
 *
 * @author Mr.Xie
 * @date 2021/12/25
 * @email xiezhyan@126.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({MySQLMarkerConfiguration.class, MySQLInitialConfigurator.class})
public @interface EnableDistributedMySQLPrimary {
}
