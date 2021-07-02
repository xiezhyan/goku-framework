package top.zopx.starter.distribution.aspect;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import top.zopx.starter.distribution.annotation.Distribution;
import top.zopx.starter.distribution.configurator.ZookeeperInitialConfigurator;
import top.zopx.starter.distribution.service.ILockService;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Objects;


/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
@Aspect
@Order(1)
@ConditionalOnMissingBean({ZookeeperInitialConfigurator.class})
public class DistributionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributionAspect.class);

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private static final DefaultParameterNameDiscoverer DISCOVERER = new DefaultParameterNameDiscoverer();

    @Resource
    private ILockService lockService;

    @Pointcut(value = "@annotation(top.zopx.starter.distribution.annotation.Distribution)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final Distribution annotation = getAnnotationDistribution(joinPoint);

        String key = annotation.value();
        if (StringUtils.contains(key, "#")) {
            key = getKeyBySpel(key, getMethod(joinPoint), joinPoint.getArgs());
        }
        try {
            lockService.lock(key);
            return joinPoint.proceed();
        } catch (Throwable e) {
            LOGGER.debug("执行任务出现异常，异常信息", e);
            throw e;
        } finally {
            // 解锁
            lockService.unLock(key);
        }
    }

    private String getKeyBySpel(String key, Method method, Object[] args) {
        String[] paramNames = DISCOVERER.getParameterNames(method);
        Expression expression = PARSER.parseExpression(key);
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < args.length; i++) {
            if (null != paramNames && ArrayUtils.isNotEmpty(paramNames)) {
                context.setVariable(paramNames[i], args[i]);
            }
        }
        return Objects.requireNonNull(expression.getValue(context)).toString();
    }

    public Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    public Distribution getAnnotationDistribution(ProceedingJoinPoint joinPoint) {
        return getMethod(joinPoint).getDeclaredAnnotation(Distribution.class);
    }
}
