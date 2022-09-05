package top.zopx.goku.framework.web.util.bind.aop;

import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.tools.constant.IEnum;
import top.zopx.goku.framework.tools.util.reflection.ReflectionClassUtil;
import top.zopx.goku.framework.tools.util.string.StringUtil;
import top.zopx.goku.framework.web.configurator.base.IAspect;
import top.zopx.goku.framework.web.configurator.base.IAspectMethod;
import top.zopx.goku.framework.web.context.SpringContext;
import top.zopx.goku.framework.web.util.bind.annotation.Bind;
import top.zopx.goku.framework.web.util.bind.annotation.Binding;
import top.zopx.goku.framework.web.util.bind.annotation.Desensitization;
import top.zopx.goku.framework.web.util.bind.interfaces.IBinding;
import top.zopx.goku.framework.web.util.bind.registry.BindingAdapterFactory;
import top.zopx.goku.framework.web.util.bind.registry.TranslateGenericConvert;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Aspect
@Component
@Order(2)
@SuppressWarnings("all")
public class TranslatorAspect implements IAspect, IAspectMethod {

    @Resource
    private BindingAdapterFactory bindingAdapter;

    @Override
    @Pointcut("@annotation(top.zopx.goku.framework.web.util.bind.annotation.Bind) " +
            "|| @within(top.zopx.goku.framework.web.util.bind.annotation.Bind)" +
            "|| execution(* top.zopx.goku.framework.mybatis.base.BaseController.*(..))")
    public void doPointcut() {
        // TODO document why this method is empty
    }

    @Override
    @AfterReturning(pointcut = "doPointcut()", returning = "returing")
    @SuppressWarnings("all")
    public void doAfterReturn(JoinPoint joinPoint, Object returing) {

        // 处理泛型
        final TranslateGenericConvert<Object> translateGenericConvert = bindingAdapter.getTranslateGenericConvert((Class<Object>) returing.getClass());
        Object result;
        if (Objects.nonNull(translateGenericConvert)) {
            result = translateGenericConvert.apply(returing);
        } else {
            result = returing;
        }

        if (result instanceof String || result instanceof Boolean || result instanceof Number) {
            // 不需要处理的返回类型，直接跳过不操作
            return;
        }

        Method method = resolveMethod(joinPoint);
        if (result instanceof Collection<?>) {
            Collection<?> list = (Collection<?>) result;
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            list.forEach(item -> translateObject(item, method));
        } else {
            this.translateObject(result, method);
        }
    }

    private void translateObject(Object result, Method method) {
        if (Objects.isNull(result)) {
            return;
        }
        List<Field> fields = ReflectionClassUtil.getFieldList(result);
        // 翻译当前对象字段
        fields.stream().filter(field -> field.isAnnotationPresent(Binding.class)).forEach(field -> translateField(result, field, method));
        // 脱敏当前字段数据
        fields.stream().filter(field -> field.isAnnotationPresent(Desensitization.class)).forEach(field -> desensitizationField(result, field));

        // 如果内部字段存在Bind注解，说明属性为对象，需要继续处理
        fields.stream().filter(field -> field.isAnnotationPresent(Bind.class)).forEach(field -> {
            Object subObject = ReflectionClassUtil.invokeGetMethod(result, field);
            if (subObject instanceof Collection<?>) {
                ((Collection<?>) subObject).forEach(result1 -> translateObject(result1, method));
            } else {
                this.translateObject(subObject, method);
            }
        });
    }

    /**
     * 对具体字段属性进行处理
     *
     * @param vo     result
     * @param field  需要设置内容的字段
     * @param method
     */
    @SuppressWarnings("unchecked")
    private void translateField(Object vo, Field field, Method method) {
        Binding annotation = field.getAnnotation(Binding.class);
        Class<? extends IEnum> enunDataSourceClazz = annotation.dataSource();

        // 从Result中得到from字段上对应的值
        Object originValue = ReflectionClassUtil.invokeGetMethod(vo, annotation.value());
        if (Objects.isNull(originValue)) {
            return;
        }

        // 得到对应的转换器
        IBinding<Object, Object> binding = SpringContext.getBean(annotation.translate());
        // 拿出转化结果
        Object translate = binding.translate(originValue, annotation.dataSource(), annotation.param(), annotation.condition(), method);
        if (Objects.isNull(translate)) {
            return;
        }

        ReflectionClassUtil.invokeSetMethod(vo, field, translate);
    }


    /**
     * 脱敏处理
     *
     * @param result result
     * @param field  需要设置内容的字段
     */
    private void desensitizationField(Object result, Field field) {
        Desensitization annotation = field.getAnnotation(Desensitization.class);

        Object originValue = ReflectionClassUtil.invokeGetMethod(result, field);
        if (Objects.isNull(originValue)) {
            return;
        }

        ReflectionClassUtil.invokeSetMethod(result, field, desensitization(originValue, field, annotation));
    }

    private Object desensitization(Object originValue, Field field, Desensitization annotation) {
        if (!(originValue instanceof String)) {
            // 不是字符串 直接返回
            return originValue;
        }
        String desensitizeResult = originValue.toString();

        if (StringUtil.isBlank(desensitizeResult)) {
            // 空字符串不处理，直接返回
            return desensitizeResult;
        }

        return desensitization(desensitizeResult, annotation.startIndex(), annotation.endIndex(), annotation.charMaskLast());
    }

    private String desensitization(String desensitizeResult, int start, int end, String charMaskLast) {
        int length = StringUtil.isNotBlank(charMaskLast) ? desensitizeResult.indexOf(charMaskLast) : desensitizeResult.length();

        if (length < start) {
            // 不够开始位置，直接返回
            return desensitizeResult;
        }

        if (start == 0 && end == 0) {
            // 全部替换
            return append(length, "*") + (StringUtil.isNotBlank(charMaskLast) ? desensitizeResult.substring(length) : "");
        }

        if (start != 0 && end == 0) {
            // 替换start之后
            return desensitizeResult.substring(0, start) + append(length - start, "*");
        }

        if (start == 0 && end != 0) {
            // 替换end之前的
            return append(length - end, "*") + desensitizeResult.substring(length - end);
        }

        if (length < start + end) {
            // 不够start + end的位置，直接将start之后的全部去掉，换成*
            return desensitizeResult.substring(0, start) + append(length - start, "*");
        }

        // 最后还留着的位数
        int middleLen = length - start - end;

        return desensitizeResult.substring(0, start) + append(middleLen, "*") + desensitizeResult.substring(start + middleLen);
    }

    private String append(int middleLen, String mask) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < middleLen; i++) {
            sb.append(mask);
        }
        return sb.toString();
    }
}
