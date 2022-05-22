package top.zopx.goku.framework.log.configurator.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import top.zopx.goku.framework.log.annotations.OperatorLogAnnotation;
import top.zopx.goku.framework.log.constant.LogConstant;
import top.zopx.goku.framework.log.event.ApiLogEvent;
import top.zopx.goku.framework.web.base.IAspect;
import top.zopx.goku.framework.web.context.GlobalContext;
import top.zopx.goku.framework.web.context.SpringContext;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 俗世游子
 * @date 2021/4/16
 */
@Aspect
@Order(1)
public class ApiLogAspect implements IAspect {

    @Pointcut(value = "@annotation(top.zopx.goku.framework.log.annotations.OperatorLogAnnotation)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 基本方法设置
        Map<String, Object> map = new HashMap<>();
        final MethodSignature signature = resolveMethodSignature(joinPoint);

        map.put(LogConstant.PARAMS, SpringContext.getJson().toJson(resolveArgs(joinPoint)));
        map.put(LogConstant.CLASS_NAME, signature.getDeclaringTypeName());
        map.put(LogConstant.METHOD_NAME, signature.getName());
        map.put(LogConstant.VALUE, signature.getMethod().getDeclaredAnnotation(OperatorLogAnnotation.class).value());

        long startTime = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        map.put(LogConstant.EXEC_TIME, System.currentTimeMillis() - startTime);
        publish(map, result);
        return result;
    }

    public void publish(Map<String, Object> map, Object result) {
        addRequestInfo(GlobalContext.CurrentRequest.getRequest(), map);
        map.put(LogConstant.RESULT, SpringContext.getJson().toJson(result));
        SpringContext.publishEvent(new ApiLogEvent(map));
    }


    public static void addRequestInfo(HttpServletRequest request, Map<String, Object> map) {
        map.put(LogConstant.REQUEST_URI, getPath(request.getRequestURI()));
        map.put(LogConstant.IP, GlobalContext.CurrentRequest.getBrowserIp());
        map.put(LogConstant.AGENT, GlobalContext.CurrentRequest.getBrowserAgent());
        map.put(LogConstant.REFERENCE, GlobalContext.CurrentRequest.getBrowserRefer());
        map.put(LogConstant.CREATE_TIME, LocalDateTime.now());
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
