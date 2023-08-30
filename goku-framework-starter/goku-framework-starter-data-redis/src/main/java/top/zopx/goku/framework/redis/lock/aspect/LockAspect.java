package top.zopx.goku.framework.redis.lock.aspect;


import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import top.zopx.goku.framework.redis.lock.Locked;
import top.zopx.goku.framework.redis.lock.properties.BootstrapLock;
import top.zopx.goku.framework.redis.lock.service.ILock;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/23 7:38
 */
@Aspect
public class LockAspect {
    private static final SpelExpressionParser SPEL_PARSER = new SpelExpressionParser();
    private static final DefaultParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    @Resource
    private BootstrapLock bootstrapLock;
    @Resource
    private ILock lock;

    private static final Logger LOGGER = LoggerFactory.getLogger(LockAspect.class);

    @Around("@annotation(locked)")
    public Object doAround(ProceedingJoinPoint joinPoint, Locked locked) throws Throwable {
        String lockKey = parseLockKey(joinPoint, locked);
        LOGGER.debug("开始加锁：lockKey = {}, threadId = {}", lockKey, Thread.currentThread().getName());

        if (lock.lock(lockKey, locked.expire(), locked.waitTime(), locked.unit())) {
            try {
                return joinPoint.proceed();
            } finally {
                lock.unLock();
            }
        } else {
            // 加锁失败，执行抛异常
            int maxWaitTime = locked.waitTime();
            String errorMsg = locked.errorMsg();
            boolean isSpringEl = errorMsg.contains("+") && errorMsg.contains("#");
            if (isSpringEl) {
                // 存储上下文参数信息，用于解析spEL表达式
                EvaluationContext evaluationContext = new StandardEvaluationContext();
                // 如果是spEL表达式就解析它
                evaluationContext.setVariable("maxWaitTime", maxWaitTime);
                errorMsg = SPEL_PARSER.parseExpression(errorMsg).getValue(evaluationContext, String.class);
            }
            throw new BusException(errorMsg, IBus.ERROR_CODE);
        }
    }

    private String parseLockKey(ProceedingJoinPoint joinPoint, Locked locked) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] lockKey = getLockKey(locked);

        String classAndMethodName = getClassAndMethodName(joinPoint);
        String lockKeyPrefix = bootstrapLock.getPrefix() + ":" + classAndMethodName;
        if (lockKey.length == 0) {
            return lockKeyPrefix;
        } else {
            // 解析springEl
            String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
            if (parameterNames == null || parameterNames.length == 0) {
                // 方法没有入参
                return lockKeyPrefix;
            }

            EvaluationContext evaluationContext = new StandardEvaluationContext();
            // 获取方法参数值
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                // 替换表达式里的变量值为实际值
                evaluationContext.setVariable(parameterNames[i], args[i]);
            }
            try {
                return Stream.of(lockKey)
                        .map(exp -> SPEL_PARSER.parseExpression(exp).getValue(evaluationContext, String.class))
                        .collect(Collectors.joining(",", lockKeyPrefix + ":(", ")"));
            } catch (RuntimeException e) {
                throw new BusException(classAndMethodName + "上的注解属性指定有误，无法解析spEl表达式", IBus.ERROR_CODE);
            }
        }
    }

    private String[] getLockKey(Locked lock) {
        int keyLength = lock.key().length;
        return keyLength > 0 ? lock.key() : lock.value();
    }

    private String getClassAndMethodName(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return ":" + joinPoint.getTarget().getClass().getName() + "." + method.getName();
    }
}
