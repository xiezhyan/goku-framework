package top.zopx.goku.framework.http.configurator.aop;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/16 22:24
 */
public abstract class BaseSpELAspect {

    private static final SpelExpressionParser SPEL_PARSER = new SpelExpressionParser();
    private static final DefaultParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * 解析SpEL
     * @param method 方法
     * @param args 具体参数信息
     * @param ext SpEL表达式
     * @param clazz 返回类型
     * @return T
     */
    protected <T> T parseExpression(Method method, Object[] args, String ext, Class<T> clazz) {
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);

        if (ArrayUtils.isEmpty(parameterNames)) {
            return null;
        }

        for (int i = 0; i < args.length; i++) {
            evaluationContext.setVariable(parameterNames[i], args[i]);
        }

        return SPEL_PARSER.parseExpression(ext).getValue(evaluationContext, clazz);
    }
}
