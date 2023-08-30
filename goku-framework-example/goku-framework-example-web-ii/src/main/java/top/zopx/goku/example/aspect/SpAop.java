package top.zopx.goku.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.http.annotation.AnnoLog;
import top.zopx.goku.framework.http.configurator.aop.BaseSpELAspect;
import top.zopx.goku.framework.http.configurator.aop.IAspect;
import top.zopx.goku.framework.http.util.log.LogHelper;

import java.lang.reflect.Method;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/16 21:40
 */
@Aspect
@Component
public class SpAop extends BaseSpELAspect implements IAspect {

    @Pointcut("@annotation(top.zopx.goku.framework.http.annotation.AnnoLog)")
    @Override
    public void doPointcut() {

    }

    @Before("doPointcut()")
    @Override
    public void doBefore(JoinPoint joinPoint) {
        IAspect.super.doBefore(joinPoint);

        Method method = resolveToMethod(joinPoint);
        Object[] args = resolveArgs(joinPoint);
        AnnoLog annoLog = method.getDeclaredAnnotation(AnnoLog.class);

        String ext = annoLog.value();

        if (ext.contains("#")) {
            LogHelper.getLogger(SpAop.class).info("content = {}", parseExpression(method, args, ext, String.class));
        }
    }



    private void printArray(String[] parameterNames, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            LogHelper.getLogger(SpAop.class).info("param {} == args {}", parameterNames[i], args[i]);
        }
    }
}
