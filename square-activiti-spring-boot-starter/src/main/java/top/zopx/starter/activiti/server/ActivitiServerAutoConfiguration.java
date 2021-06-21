package top.zopx.starter.activiti.server;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.zopx.starter.activiti.controller.ActivitiControllerConfiguration;

/**
 * @author sanq.Yan
 * @date 2021/4/18
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(ActivitiMarkerConfiguration.ActivitiMarker.class)
@Import({ActivitiControllerConfiguration.class})
public class ActivitiServerAutoConfiguration {}
