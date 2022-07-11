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
import top.zopx.goku.framework.web.util.bind.annotation.Bind;
import top.zopx.goku.framework.web.util.bind.annotation.Binding;
import top.zopx.goku.framework.web.util.bind.interfaces.IBinding;
import top.zopx.goku.framework.web.util.bind.registry.BindingAdapterFactory;
import top.zopx.goku.framework.web.util.bind.registry.TranslateGenericConvert;
import top.zopx.goku.framework.web.configurator.base.IAspect;
import top.zopx.goku.framework.web.context.SpringContext;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Aspect
@Component
@Order(2)
@SuppressWarnings("all")
public class TranslatorAspect implements IAspect {

    @Resource
    private BindingAdapterFactory bindingAdapter;

    @Override
    @Pointcut("@annotation(top.zopx.goku.framework.web.util.bind.annotation.Bind)")
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

        if (result instanceof Collection<?>) {
            Collection<?> list = (Collection<?>) result;
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            list.forEach(this::translateObject);
        } else {
            this.translateObject(result);
        }
    }

    private void translateObject(Object result) {
        if (Objects.isNull(result)) {
            return;
        }
        List<Field> fields = ReflectionClassUtil.getFieldList(result);
        // 翻译当前对象字段
        fields.stream().filter(field -> field.isAnnotationPresent(Binding.class)).forEach(field -> translateField(result, field));

        // 如果内部字段存在Bind注解，说明属性为对象，需要继续处理
        fields.stream().filter(field -> field.isAnnotationPresent(Bind.class)).forEach(field -> {
            Object subObject = ReflectionClassUtil.invokeGetMethod(result, field);
            if (subObject instanceof Collection<?>) {
                ((Collection<?>) subObject).forEach(this::translateObject);
            } else {
                this.translateObject(subObject);
            }
        });
    }

    /**
     * 对具体字段属性进行处理
     *
     * @param vo    result
     * @param field 需要设置内容的字段
     */
    @SuppressWarnings("unchecked")
    private void translateField(Object vo, Field field) {
        Binding annotation = field.getAnnotation(Binding.class);
        Class<? extends IEnum> enunDataSourceClazz = annotation.dataSource();

        // 从Result中得到from字段上对应的值
        Object originValue = ReflectionClassUtil.invokeGetMethod(vo, annotation.value());
        if (Objects.isNull(originValue)) {
            return;
        }

        // 得到对应的转换器
        IBinding<Object> binding = SpringContext.getBean(annotation.translate());
        // 拿出转化结果
        String translate = binding.translate(originValue, annotation.dataSource(), annotation.param());
        if (StringUtil.isBlank(translate)) {
            return;
        }
        // 复制到对应的字段中
        ReflectionClassUtil.invokeSetMethod(vo, field, translate);
    }
}
