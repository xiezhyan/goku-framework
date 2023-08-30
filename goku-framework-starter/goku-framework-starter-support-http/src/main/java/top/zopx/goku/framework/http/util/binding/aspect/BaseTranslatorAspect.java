package top.zopx.goku.framework.http.util.binding.aspect;

import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.core.annotation.Order;
import top.zopx.goku.framework.http.configurator.aop.IAspect;
import top.zopx.goku.framework.http.context.SpringContext;
import top.zopx.goku.framework.http.util.binding.annotation.Bind;
import top.zopx.goku.framework.http.util.binding.annotation.Binding;
import top.zopx.goku.framework.http.util.binding.interfaces.IBinding;
import top.zopx.goku.framework.http.util.binding.registry.BindingAdapterFactory;
import top.zopx.goku.framework.http.util.binding.registry.TranslateGenericConvert;
import top.zopx.goku.framework.tools.dict.IEnum;
import top.zopx.goku.framework.tools.util.reflection.ReflectionClassUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Order(2)
@SuppressWarnings("all")
public abstract class BaseTranslatorAspect implements IAspect {

    @Resource
    private BindingAdapterFactory bindingAdapter;

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
        Object translate = binding.translate(originValue, annotation.dataSource(), annotation.param(), method);
        if (Objects.isNull(translate)) {
            return;
        }

        ReflectionClassUtil.invokeSetMethod(vo, field, translate);
    }
}
