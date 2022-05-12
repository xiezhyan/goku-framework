package top.zopx.starter.log.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import top.zopx.starter.log.constant.LogConstant;
import top.zopx.starter.tools.tools.date.LocalDateUtil;
import top.zopx.starter.tools.tools.json.IJson;
import top.zopx.starter.tools.tools.json.JsonUtil;
import top.zopx.starter.tools.tools.web.GlobalUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

/**
 * @author 俗世游子
 * @date 2021/4/12
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext context;
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringUtil.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return clazz == null ? null : context.getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null != beanName && !"".equals(beanName.trim())) {
            return clazz == null ? null : context.getBean(beanName, clazz);
        } else {
            return null;
        }
    }

    public static IJson getJson() {
        return Optional.ofNullable(getBean(IJson.class)).orElse(JsonUtil.getInstance().getJson());
    }

    public static void publishEvent(ApplicationEvent event) {
        if (context != null) {
            try {
                context.publishEvent(event);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
    }

    public static void addRequestInfo(HttpServletRequest request, Map<String, Object> map) {
        map.put(LogConstant.REQUEST_URI, getPath(request.getRequestURI()));
        map.put(LogConstant.IP, GlobalUtil.Request.getBrowserIp(request));
        map.put(LogConstant.AGENT, GlobalUtil.Request.getBrowserAgent(request));
        map.put(LogConstant.REFERENCE, GlobalUtil.Request.getBrowserRefer(request));
        map.put(LogConstant.CREATE_TIME, LocalDateUtil.INSTANCE.nowDateTime());
        map.put(LogConstant.REQUEST_TYPE, request.getMethod());
    }

    public static String getPath(String uriStr) {
        URI uri;
        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException var3) {
            throw new RuntimeException(var3);
        }

        return uri.getPath();
    }

}
