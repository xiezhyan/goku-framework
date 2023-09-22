package top.zopx.goku.framework.http.util.binding.registry.wrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import top.zopx.goku.framework.http.util.binding.registry.BaseBindingAdapter;
import top.zopx.goku.framework.http.util.binding.registry.TranslateGenericConvert;
import top.zopx.goku.framework.tools.entity.vo.Page;
import top.zopx.goku.framework.tools.entity.wrapper.R;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/07/11 18:28
 */
@Order(1000)
@Configuration
public class ResultWrapperConvert {

    public static class ResultWrapperTranslateTypeConvert implements TranslateGenericConvert<R> {
        @Override
        public Object apply(R r) {
            if (r.getData() instanceof Page) {
                return ((Page<?>) r.getData()).getData();
            }
            return r.getData();
        }
    }

    @Bean
    public BaseBindingAdapter initResultConvert() {
        return new BaseBindingAdapter() {
            @Override
            public TranslateGenericConvert addTranslateGenericConvert() {
                return new ResultWrapperTranslateTypeConvert();
            }
        };
    }
}
