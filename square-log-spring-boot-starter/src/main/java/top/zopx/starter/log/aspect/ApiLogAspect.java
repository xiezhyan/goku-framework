package top.zopx.starter.log.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import top.zopx.starter.log.constant.LogConstant;
import top.zopx.starter.log.listener.api.ApiPublish;
import top.zopx.starter.tools.tools.json.JsonUtil;
import top.zopx.starter.tools.tools.web.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sanq.Yan
 * @date 2021/4/16
 */
@Aspect
@Order(1)
public class ApiLogAspect {

    @Pointcut(value = "@annotation(top.zopx.starter.log.annotations.OperatorLogAnnotation)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 基本方法设置
        Map<String, Object> map = new HashMap<>();
        map.put(LogConstant.PARAMS, JsonUtil.toJson(joinPoint.getArgs()));
        map.put(LogConstant.CLASS_NAME, joinPoint.getSignature().getDeclaringTypeName());
        map.put(LogConstant.METHOD_NAME, joinPoint.getSignature().getName());

        final Object result = joinPoint.proceed();
        ApiPublish.publish(map, result);
        return result;
    }
}
