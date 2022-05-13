package top.zopx.starter.log.event.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import top.zopx.starter.log.configurator.properties.SquareLogProperties;
import top.zopx.starter.log.constant.LogConstant;
import top.zopx.starter.log.event.ApiLogEvent;
import top.zopx.starter.log.event.ErrorLogEvent;
import top.zopx.starter.log.service.ILogService;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * @author 俗世游子
 * @date 2021/4/17
 */
@Component
public class PublishEventListener {

    @Resource
    private ILogService logService;

    @Resource
    private SquareLogProperties squareLogProperties;

    @Value("${spring.application.name}")
    private String applicationName;

    @EventListener(value = ErrorLogEvent.class)
    @SuppressWarnings("all")
    public void onApplicationEvent(ErrorLogEvent event) {
        final Map<String, Object> source = (Map<String, Object>) event.getSource();
        source.put(LogConstant.APP_NAME, Optional.ofNullable(squareLogProperties.getAppName()).orElse(applicationName));
        logService.saveError(source);
    }

    @EventListener(value = ApiLogEvent.class)
    @SuppressWarnings("all")
    public void onApplicationEvent(ApiLogEvent event) {
        final Map<String, Object> source = (Map<String, Object>) event.getSource();
        source.put(LogConstant.APP_NAME, Optional.ofNullable(squareLogProperties.getAppName()).orElse(applicationName));
        logService.saveApi(source);
    }
}
