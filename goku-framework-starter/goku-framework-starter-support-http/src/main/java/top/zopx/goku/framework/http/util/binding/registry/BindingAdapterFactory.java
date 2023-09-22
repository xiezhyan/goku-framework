package top.zopx.goku.framework.http.util.binding.registry;

import org.springframework.stereotype.Component;
import top.zopx.goku.framework.tools.util.reflection.ReflectionClassUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/07/11 18:29
 */
@Component("bindingAdapter")
public class BindingAdapterFactory {

    private static final Map<Class<?>, TranslateGenericConvert> CACHE_MAP = new ConcurrentHashMap<>();

    public <T> void convertToCache(TranslateGenericConvert<T> translateTypeConvert) {
        final List<Class<?>> classes = ReflectionClassUtil.getGeneric(translateTypeConvert.getClass());
        CACHE_MAP.put(classes.get(0), translateTypeConvert);
    }

    public <T> TranslateGenericConvert<T> getTranslateGenericConvert(Class<T> convertClass) {
        return CACHE_MAP.get(convertClass);
    }

}
