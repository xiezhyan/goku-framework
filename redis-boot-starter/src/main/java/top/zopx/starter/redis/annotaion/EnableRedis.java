package top.zopx.starter.redis.annotaion;

import org.springframework.context.annotation.Import;
import top.zopx.starter.redis.auto.RedisAutoConfig;

import java.lang.annotation.*;

/**
 * top.zopx.starter.redis.annotaion.EnableRedis
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedisAutoConfig.class)
public @interface EnableRedis {
}
