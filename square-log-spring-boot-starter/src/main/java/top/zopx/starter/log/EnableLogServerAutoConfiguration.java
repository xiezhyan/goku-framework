package top.zopx.starter.log;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import top.zopx.starter.log.advice.ExceptionAdvice;
import top.zopx.starter.log.listener.ErrorEventListener;
import top.zopx.starter.log.util.SpringUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sanq.Yan
 * @date 2021/4/12
 */
@Import({
        ExceptionAdvice.class,
        SpringUtil.class,
        ErrorEventListener.class
})
public class EnableLogServerAutoConfiguration {
}
