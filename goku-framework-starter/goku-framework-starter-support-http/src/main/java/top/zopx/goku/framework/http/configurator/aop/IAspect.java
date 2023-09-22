package top.zopx.goku.framework.http.configurator.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import top.zopx.goku.framework.http.util.log.LogHelper;
import top.zopx.goku.framework.tools.util.json.GsonUtil;
import top.zopx.goku.framework.tools.util.reflection.ReflectionClassUtil;

import java.lang.reflect.Method;

/**
 * @author Mr.Xie
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

    /**
     * 在进入方法之前
     * 可以加入额外参数等信息
     */
    default void doBefore(JoinPoint joinPoint) {
        LogHelper.getLogger(IAspect.class).debug("doBefore执行： clazzName = {}", this.getClass().getSimpleName());
    }

    /**
     * 在方法执行之后的操作
     */
    default void doAfter(JoinPoint joinPoint) {
        LogHelper.getLogger(IAspect.class).debug("doAfter执行： clazzName = {}", this.getClass().getSimpleName());
    }

    /**
     * 只有方法成功完成后才能在方法执行后运行通知
     *
     * @param joinPoint 参数
     * @param returing  返回结果信息
     */
    default void doAfterReturn(JoinPoint joinPoint, Object returing) {
        LogHelper.getLogger(IAspect.class).debug("{}", GsonUtil.getInstance().toJson(returing));
    }

    /**
     * 环绕通知
     *
     * @param joinPoint 参数
     * @return 处理结果
     * @throws Throwable 异常
     */
    default Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    /**
     * 通过aop的ProceedingJoinPoint 参数，获取到请求方法
     *
     * @param joinPoint 参数
     * @return Method
     */
    default Method resolveMethod(JoinPoint joinPoint) {
        MethodSignature signature = resolveMethodSignature(joinPoint);
        Class<?> targetClass = joinPoint.getTarget().getClass();

        Method method = ReflectionClassUtil.getMethod(targetClass, signature.getName(),
                signature.getMethod().getParameterTypes());
        if (method == null) {
            throw new IllegalStateException("Cannot resolve target method: " + signature.getMethod().getName());
        }
        return method;
    }

    default MethodSignature resolveMethodSignature(JoinPoint joinPoint) {
        return (MethodSignature) joinPoint.getSignature();
    }

    default Method resolveToMethod(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }

    /**
     * 获取参数
     *
     * @param joinPoint 入参
     * @return Object[]
     */
    default Object[] resolveArgs(JoinPoint joinPoint) {
        return joinPoint.getArgs();
    }
}
