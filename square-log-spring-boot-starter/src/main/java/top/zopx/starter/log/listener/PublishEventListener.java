package top.zopx.starter.log.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import top.zopx.starter.log.constant.LogConstant;
import top.zopx.starter.log.event.ApiLogEvent;
import top.zopx.starter.log.event.ErrorLogEvent;
import top.zopx.starter.log.properties.SquareLogProperties;
import top.zopx.starter.log.service.ILogService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author sanq.Yan
 * @date 2021/4/17
 */
@Component
public class PublishEventListener {

    @Resource
    private ILogService logService;

    @Resource
    private SquareLogProperties squareLogProperties;

    @EventListener(value = ErrorLogEvent.class)
    @SuppressWarnings("all")
    public void onApplicationEvent(ErrorLogEvent event) {
        final Map<String, Object> source = (Map<String, Object>) event.getSource();
        source.put(LogConstant.APP_NAME, squareLogProperties.getAppName());
        logService.saveError(source);
    }

    @EventListener(value = ApiLogEvent.class)
    @SuppressWarnings("all")
    public void onApplicationEvent(ApiLogEvent event) {
        final Map<String, Object> source = (Map<String, Object>) event.getSource();
        source.put(LogConstant.APP_NAME, squareLogProperties.getAppName());
        logService.saveApi(source);
    }
}
