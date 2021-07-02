package top.zopx.starter.distribution.aspect;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import top.zopx.starter.distribution.annotation.Distribution;
import top.zopx.starter.distribution.configurator.ZookeeperInitialConfigurator;
import top.zopx.starter.distribution.properties.SquareDistributionProperties;

import javax.annotation.Resource;
import java.lang.reflect.Method;


/**
 * @author sanq.Yan
 * @date 2021/3/28
 */
@Aspect
@Order(1)
@ConditionalOnBean({ZookeeperInitialConfigurator.class})
public class DistributionZkAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributionZkAspect.class);

    @Resource
    private SquareDistributionProperties squareDistributionProperties;
    @Resource
    private CuratorFramework curatorFramework;

    @Pointcut(value = "@annotation(top.zopx.starter.distribution.annotation.Distribution)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        final Distribution annotation = getAnnotationDistribution(joinPoint);

        String key = annotation.value();
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, squareDistributionProperties.getZookeeper().getRoot() + key);

        try {
            interProcessMutex.acquire();
            LOGGER.info("=============starting lock, {}， {}=============", key, Thread.currentThread().getName());
            return joinPoint.proceed();
        } catch (Throwable e) {
            LOGGER.error("执行任务出现异常，异常信息", e);
            throw e;
        } finally {
            // 解锁
            LOGGER.info("=============starting unlock, {}， {}=============", key, Thread.currentThread().getName());
            interProcessMutex.release();
        }
    }

    public Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    public Distribution getAnnotationDistribution(ProceedingJoinPoint joinPoint) {
        return getMethod(joinPoint).getDeclaredAnnotation(Distribution.class);
    }
}
