package top.zopx.starter.activiti.annotation;

import org.springframework.context.annotation.Import;
import top.zopx.starter.activiti.server.ActivitiMarkerConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sanq.Yan
 * @date 2021/4/18
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Import({ActivitiMarkerConfiguration.class})
public @interface EnableActivitiDashboard {
}
