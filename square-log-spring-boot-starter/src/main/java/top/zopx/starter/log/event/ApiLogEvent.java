package top.zopx.starter.log.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @author sanq.Yan
 * @date 2021/4/12
 */
public class ApiLogEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public ApiLogEvent(Map<String, Object> source) {
        super(source);
    }
}
