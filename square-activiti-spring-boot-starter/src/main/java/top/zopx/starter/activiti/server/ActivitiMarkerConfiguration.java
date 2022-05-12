package top.zopx.starter.activiti.server;

import org.springframework.context.annotation.Bean;

/**
 * @author 俗世游子
 * @date 2021/4/18
 */
public class ActivitiMarkerConfiguration {
    @Bean
    public ActivitiMarker activitiMarker() {
        return new ActivitiMarker();
    }

    public static class ActivitiMarker {
    }
}
