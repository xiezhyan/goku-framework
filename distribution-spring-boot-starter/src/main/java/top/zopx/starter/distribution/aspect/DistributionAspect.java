package top.zopx.starter.distribution.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;
import org.springframework.web.servlet.support.RequestContextUtils;
import top.zopx.starter.distribution.annotation.AnnotationDistribution;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;


/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
@Aspect
@Component
@Order(1)
public class DistributionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributionAspect.class);

    @Pointcut(value = "@annotation(top.zopx.starter.distribution.annotation.AnnotationDistribution)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        final AnnotationDistribution annotation = getAnnotationDistribution(joinPoint);

        LOGGER.debug("执行任务开始，加锁:{} > {}", annotation.key(), Thread.currentThread().getName());

        try {
            Object proceed = joinPoint.proceed();
            return proceed;
        } catch (Throwable e) {
            LOGGER.debug("执行任务出现异常，异常信息", e);
            throw e;
        } finally {
            // 解锁
            LOGGER.debug("执行任务结束，解锁:{} > {}", annotation.key(), Thread.currentThread().getName());
        }
    }

    public AnnotationDistribution getAnnotationDistribution(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(AnnotationDistribution.class);
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest();
    }
}
