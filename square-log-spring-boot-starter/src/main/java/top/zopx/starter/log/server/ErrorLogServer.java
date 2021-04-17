package top.zopx.starter.log.server;

import org.springframework.context.annotation.Import;
import top.zopx.starter.log.advice.ExceptionAdvice;

/**
 * @author sanq.Yan
 * @date 2021/4/16
 */
@Import({
        ExceptionAdvice.class
})
public class ErrorLogServer {
}
