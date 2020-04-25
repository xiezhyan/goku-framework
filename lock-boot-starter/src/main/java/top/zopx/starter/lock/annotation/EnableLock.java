package top.zopx.starter.lock.annotation;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Import;
import top.zopx.starter.lock.auto.LockAutoConfig;

import java.lang.annotation.*;

/**
 * top.zopx.starter.lock.annotation.EnableLock
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LockAutoConfig.class})
public @interface EnableLock {
}
