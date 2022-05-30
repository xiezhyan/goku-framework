package top.zopx.goku.framework.log.configurator.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.zopx.goku.framework.tools.entity.wrapper.R;
import top.zopx.goku.framework.web.util.LogHelper;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/24 19:26
 */
public abstract class BaseResponseResultAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        final boolean b = returnType.getParameterType().equals(R.class);
        LogHelper.getLogger(BaseResponseResultAdvice.class).info("开始统一结果，返回是否为R，结果 = {}", b);
        return !b;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        LogHelper.getLogger(BaseResponseResultAdvice.class).info("开始统一结果， returnType = {}", returnType.getParameterType().getSimpleName());
        return R.result(body);
    }
}
