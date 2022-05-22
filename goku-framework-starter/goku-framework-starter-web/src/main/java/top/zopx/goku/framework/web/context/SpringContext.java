package top.zopx.goku.framework.web.context;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.tools.util.json.IJson;
import top.zopx.goku.framework.tools.util.json.JsonUtil;
import top.zopx.goku.framework.web.util.LogHelper;

import java.util.Optional;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:46
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        SpringContext.context = applicationContext;
    }

    /**
     * 获取全局Context
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * 消息发布
     *
     * @param event 消息
     */
    public static void publishEvent(ApplicationEvent event) {
        if (context != null) {
            try {
                context.publishEvent(event);
            } catch (Exception e) {
                LogHelper.getLogger(SpringContext.class).error(e.getMessage(), e);
            }

        }
    }

    /**
     * 获取指定的Bean
     * @param clazz 类
     * @return Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return clazz == null ? null : context.getBean(clazz);
    }

    public static IJson getJson() {
        return Optional.ofNullable(getBean(IJson.class)).orElse(JsonUtil.getInstance().getJson());
    }
}
