package top.zopx.goku.framework.log.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @author 俗世游子
 * @date 2021/4/12
 */
public class ErrorLogEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public ErrorLogEvent(Map<String, Object> source) {
        super(source);
    }
}
