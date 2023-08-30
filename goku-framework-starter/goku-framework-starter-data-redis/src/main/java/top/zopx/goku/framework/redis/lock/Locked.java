package top.zopx.goku.framework.redis.lock;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 锁配置
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/23 7:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Locked {

    /**
     * key 支持SpEL表达式
     */
    @AliasFor(value = "key")
    String[] value() default {};

    /**
     * key 支持SpEL表达式
     */
    @AliasFor(value = "value")
    String[] key() default {};

    /**
     * 加锁后自动释放时间
     * 默认自动续期（-1）
     */
    int expire() default -1;

    /**
     * 最大等待时长，超过后还未加锁成功则抛出异常
     */
    int waitTime() default 5;

    /**
     * 超时后抛出异常的错误信息
     */
    String errorMsg() default "spring.data.redis.lock.errorMsg";

    /**
     * 时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
