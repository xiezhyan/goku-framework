package top.zopx.starter.tools.tools.web;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 基础aop
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/30 10:22
 */
public interface IAspect {

    /**
     * 通过aop的ProceedingJoinPoint 参数，获取到请求方法
     *
     * @param joinPoint 参数
     * @return Method
     */
    default Method resolveMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = resolveMethodSignature(joinPoint);
        Class<?> targetClass = joinPoint.getTarget().getClass();

        Method method = getDeclaredMethodFor(targetClass, signature.getName(),
                signature.getMethod().getParameterTypes());
        if (method == null) {
            throw new IllegalStateException("Cannot resolve target method: " + signature.getMethod().getName());
        }
        return method;
    }

    default MethodSignature resolveMethodSignature(ProceedingJoinPoint joinPoint) {
        return (MethodSignature) joinPoint.getSignature();
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

    private Method getDeclaredMethodFor(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethodFor(superClass, name, parameterTypes);
            }
        }
        return null;
    }

}
