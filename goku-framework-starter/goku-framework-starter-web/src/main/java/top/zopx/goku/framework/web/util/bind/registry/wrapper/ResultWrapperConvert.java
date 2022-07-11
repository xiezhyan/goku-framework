package top.zopx.goku.framework.web.util.bind.registry.wrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zopx.goku.framework.tools.entity.wrapper.R;
import top.zopx.goku.framework.web.util.bind.registry.BaseBindingAdapter;
import top.zopx.goku.framework.web.util.bind.registry.TranslateGenericConvert;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/07/11 18:28
 */
@Configuration
public class ResultWrapperConvert {

    public static class ResultWrapperTranslateTypeConvert implements TranslateGenericConvert<R> {
        @Override
        public Object apply(R r) {
            return r.getData();
        }
    }

    @Bean
    public BaseBindingAdapter initResultConvert() {
        return new BaseBindingAdapter() {
            @Override
            public TranslateGenericConvert addtranslateGenericConvert() {
                return new ResultWrapperTranslateTypeConvert();
            }
        };
    }
}
