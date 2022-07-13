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
import top.zopx.goku.framework.web.context.SpringContext;
import top.zopx.goku.framework.web.util.bind.annotation.Bind;
import top.zopx.goku.framework.web.util.bind.annotation.Binding;
import top.zopx.goku.framework.web.util.bind.annotation.Desensitization;
import top.zopx.goku.framework.web.util.bind.interfaces.IBinding;
import top.zopx.goku.framework.web.util.bind.registry.BindingAdapterFactory;
import top.zopx.goku.framework.web.util.bind.registry.TranslateGenericConvert;

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
        IBinding<Object, Object> binding = SpringContext.getBean(annotation.translate());
        // 拿出转化结果
        Object translate = binding.translate(originValue, annotation.dataSource(), annotation.param());
        if (Objects.isNull(translate)) {
            return;
        }

        desensitizationField(vo, field, translate);
    }

    /**
     * 脱敏处理
     *
     * @param vo        对象
     * @param field     字段
     * @param translate 转换结果
     */
    private void desensitizationField(Object vo, Field field, Object translate) {
        // 不是字符串类型，直接处理
        if (!(translate instanceof String)) {
            ReflectionClassUtil.invokeSetMethod(vo, field, translate);
            return;
        }

        // 没有Desensitization注解，继续处理业务
        if (!field.isAnnotationPresent(Desensitization.class)) {
            ReflectionClassUtil.invokeSetMethod(vo, field, translate);
            return;
        }

        // 脱敏处理
        final Desensitization annotation = field.getAnnotation(Desensitization.class);
        ReflectionClassUtil.invokeSetMethod(vo, field, extracted(vo, field, translate, annotation));
    }

    private Object extracted(Object vo, Field field, Object translate, Desensitization annotation) {
        String desensitizeResult = translate.toString();

        if (StringUtil.isBlank(desensitizeResult)) {
            // 空字符串不处理，直接返回
            return translate;
        }

        final int length = desensitizeResult.length();
        if (length < annotation.startIndex()) {
            // 不够开始位置，直接返回
            return translate;
        }

        // 最后还留着的位数
        int middleLen = 0;
        if ((middleLen = (length - annotation.startIndex())) < annotation.endIndex()) {
            // 计算出中间的长度
            if (Objects.equals(middleLen, 0)) {
                return translate;
            }
            // 进行拼接
            return desensitizeResult.substring(0, annotation.startIndex()) + append(middleLen, annotation.mask());
        }

        middleLen -= annotation.endIndex();
        return desensitizeResult.substring(0, annotation.startIndex()) + append(middleLen, annotation.mask()) + desensitizeResult.substring(annotation.startIndex() + annotation.endIndex() + 1);
    }

    private String append(int middleLen, String mask) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < middleLen; i++) {
            sb.append(mask);
        }
        return sb.toString();
    }
}
