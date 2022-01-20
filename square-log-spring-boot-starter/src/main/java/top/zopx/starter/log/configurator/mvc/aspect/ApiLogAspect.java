package top.zopx.starter.log.configurator.mvc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import top.zopx.starter.log.annotations.OperatorLogAnnotation;
import top.zopx.starter.log.constant.LogConstant;
import top.zopx.starter.log.event.listener.api.ApiPublish;
import top.zopx.starter.tools.tools.json.impl.FJsonUtil;

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
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        map.put(LogConstant.PARAMS, FJsonUtil.INSTANCE.toJson(joinPoint.getArgs()));
        map.put(LogConstant.CLASS_NAME, signature.getDeclaringTypeName());
        map.put(LogConstant.METHOD_NAME, signature.getName());
        map.put(LogConstant.VALUE, signature.getMethod().getDeclaredAnnotation(OperatorLogAnnotation.class).value());

        long startTime = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        map.put(LogConstant.EXEC_TIME, System.currentTimeMillis() - startTime);
        ApiPublish.publish(map, result);
        return result;
    }
}
