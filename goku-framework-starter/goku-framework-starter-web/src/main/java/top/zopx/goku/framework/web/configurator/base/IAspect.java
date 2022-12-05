package top.zopx.goku.framework.web.configurator.base;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import top.zopx.goku.framework.tools.util.json.JsonUtil;
import top.zopx.goku.framework.web.util.LogHelper;

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
        LogHelper.getLogger(IAspect.class).debug("{}", JsonUtil.getInstance().toJson(returing));
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
}
