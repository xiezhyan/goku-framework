package top.zopx.starter.activiti.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;
import top.zopx.starter.activiti.server.ActivitiMarkerConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sanq.Yan
 * @date 2021/4/18
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
@ConditionalOnBean(ActivitiMarkerConfiguration.ActivitiMarker.class)
public @interface MarkRestController {
}
