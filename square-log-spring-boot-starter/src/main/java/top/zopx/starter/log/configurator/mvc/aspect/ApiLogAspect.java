package top.zopx.starter.log.configurator.mvc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import top.zopx.starter.log.annotations.OperatorLogAnnotation;
import top.zopx.starter.log.constant.LogConstant;
import top.zopx.starter.log.event.ApiLogEvent;
import top.zopx.starter.log.util.SpringUtil;
import top.zopx.starter.tools.tools.web.GlobalUtil;
import top.zopx.starter.tools.tools.web.IAspect;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 俗世游子
 * @date 2021/4/16
 */
@Aspect
@Order(1)
public class ApiLogAspect implements IAspect {

    @Pointcut(value = "@annotation(top.zopx.starter.log.annotations.OperatorLogAnnotation)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 基本方法设置
        Map<String, Object> map = new HashMap<>();
        final MethodSignature signature = resolveMethodSignature(joinPoint);

        map.put(LogConstant.PARAMS, SpringUtil.getJson().toJson(resolveArgs(joinPoint)));
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
        SpringUtil.addRequestInfo(GlobalUtil.Request.getRequest(), map);
        map.put(LogConstant.RESULT, SpringUtil.getJson().toJson(result));
        SpringUtil.publishEvent(new ApiLogEvent(map));
    }
}
