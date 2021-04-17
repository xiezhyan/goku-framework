package top.zopx.starter.log.server;

import org.springframework.context.annotation.Import;
import top.zopx.starter.log.aspect.ApiLogAspect;

/**
 * @author sanq.Yan
 * @date 2021/4/16
 */
@Import({
        ApiLogAspect.class
})
public class ApiLogServer {
}
