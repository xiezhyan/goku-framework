package top.zopx.goku.framework.web.base;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/27
 */
@Aspect
public interface IAspect {

    /**
     * 切面指示
     *
     * @Pointcut(value = "@annotation() || @within()")
     * @Pointcut(value = "execution(* *.*.*(..))")
     */
    void doPointcut();

    default void doBefore() {
    }

    default void doAfter() {
    }

    default void doAfterReturn(JoinPoint joinPoint, Object returing) {
    }

    default Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
