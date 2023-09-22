package top.zopx.goku.framework.http.configurator.record;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.http.configurator.aop.BaseSpELAspect;
import top.zopx.goku.framework.tools.util.json.GsonUtil;
import top.zopx.goku.framework.http.constant.LogConstant;
import top.zopx.goku.framework.http.annotation.AnnoLog;
import top.zopx.goku.framework.http.configurator.aop.IAspect;
import top.zopx.goku.framework.http.context.GlobalContext;
import top.zopx.goku.framework.http.context.SpringContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Xie
 * @date 2021/4/16
 */
@Aspect
@Order(1)
public class ApiLogAspect extends BaseSpELAspect implements IAspect {

    @Pointcut(value = "@annotation(top.zopx.goku.framework.http.annotation.AnnoLog)")
    @Override
    public void doPointcut() { /* document why this method is empty */ }

    @Around("doPointcut()")
    @Override
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 基本方法设置
        Map<String, Object> map = new HashMap<>(64);

        final MethodSignature signature = resolveMethodSignature(joinPoint);
        Object[] args = resolveArgs(joinPoint);
        Method method = signature.getMethod();

        AnnoLog annoLog = method.getDeclaredAnnotation(AnnoLog.class);
        if (annoLog.value().contains("#")) {
            // 解析SpEL
            map.put(LogConstant.VALUE, parseExpression(method, args, annoLog.value(), Object.class));
        } else {
            map.put(LogConstant.VALUE, annoLog.value());
        }

        map.put(LogConstant.PARAMS, GsonUtil.getInstance().toJson(args));
        map.put(LogConstant.CLASS_NAME, signature.getDeclaringTypeName());
        map.put(LogConstant.METHOD_NAME, signature.getName());

        long startTime = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        map.put(LogConstant.EXEC_TIME, System.currentTimeMillis() - startTime);
        publish(map, result);
        return result;
    }

    public void publish(Map<String, Object> map, Object result) {
        LogEvent.addRequestInfo(GlobalContext.CurrentRequest.getRequest(), map);
        map.put(LogConstant.RESULT, GsonUtil.getInstance().toJson(result));
        map.put(LogConstant.LOG_TYPE, "log");
        SpringContext.publishEvent(new LogEvent(map));
    }
}
