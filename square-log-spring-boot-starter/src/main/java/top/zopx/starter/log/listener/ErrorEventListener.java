package top.zopx.starter.log.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import top.zopx.starter.log.event.ErrorLogEvent;
import top.zopx.starter.log.service.IErrorLogService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author sanq.Yan
 * @date 2021/4/12
 */
@Component
public class ErrorEventListener implements ApplicationListener<ErrorLogEvent> {

    @Resource
    private IErrorLogService errorLogService;

    @Override
    @SuppressWarnings("all")
    public void onApplicationEvent(ErrorLogEvent event) {
        final Map<String, Object> source = (Map<String, Object>) event.getSource();
        errorLogService.save(source);
    }
}
