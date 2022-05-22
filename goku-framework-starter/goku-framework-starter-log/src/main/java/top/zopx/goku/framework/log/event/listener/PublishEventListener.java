package top.zopx.goku.framework.log.event.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.log.properties.BootstrapLog;
import top.zopx.goku.framework.log.constant.LogConstant;
import top.zopx.goku.framework.log.event.ApiLogEvent;
import top.zopx.goku.framework.log.event.ErrorLogEvent;
import top.zopx.goku.framework.log.service.ILogService;

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
    private BootstrapLog bootstrapLog;

    @Value("${spring.application.name}")
    private String applicationName;

    @EventListener(value = ErrorLogEvent.class)
    @SuppressWarnings("all")
    public void onApplicationEvent(ErrorLogEvent event) {
        final Map<String, Object> source = (Map<String, Object>) event.getSource();
        source.put(LogConstant.APP_NAME, Optional.ofNullable(bootstrapLog.getAppName()).orElse(applicationName));
        logService.saveError(source);
    }

    @EventListener(value = ApiLogEvent.class)
    @SuppressWarnings("all")
    public void onApplicationEvent(ApiLogEvent event) {
        final Map<String, Object> source = (Map<String, Object>) event.getSource();
        source.put(LogConstant.APP_NAME, Optional.ofNullable(bootstrapLog.getAppName()).orElse(applicationName));
        logService.saveApi(source);
    }
}
