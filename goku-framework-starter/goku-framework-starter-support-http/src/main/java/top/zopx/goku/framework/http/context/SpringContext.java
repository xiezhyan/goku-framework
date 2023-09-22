package top.zopx.goku.framework.http.context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.http.util.log.LogHelper;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:46
 */
@Component
public class SpringContext {

    private static ApplicationContext context;

    SpringContext(ApplicationContext context) {
        SpringContext.context = context;
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
     *
     * @param clazz 类
     * @return Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return clazz == null ? null : context.getBean(clazz);
    }

    /**
     * 指定bean
     *
     * @param beanName beanName
     * @param args     参数
     * @return 返回Bean
     */
    public static <T> T getBean(String beanName, Object... args) {
        return StringUtils.isEmpty(beanName) ? null : (T) context.getBean(beanName, args);
    }
}
