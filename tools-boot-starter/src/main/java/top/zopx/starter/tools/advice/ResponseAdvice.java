package top.zopx.starter.tools.advice;

import org.apache.commons.collections4.MapUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.zopx.starter.tools.annotations.PassResponseAdviceAnnotation;
import top.zopx.starter.tools.basic.Response;
import top.zopx.starter.tools.tools.strings.StringUtil;

import java.util.LinkedHashMap;

/**
 * 改成抽象类，如果要使用，继承就行
 * @author sanq.Yan
 * @date 2020/8/11
 */
//@RestControllerAdvice
public abstract class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        if (
                methodParameter.getDeclaringClass().isAnnotationPresent(
                        PassResponseAdviceAnnotation.class
                )
        ) {
            return false;
        }

        if (
                methodParameter.getMethod().isAnnotationPresent(
                        PassResponseAdviceAnnotation.class
                )
        ) {
            return false;
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (null == o) {
            return new Response().failure();
        }

        Response response = new Response();

        if (o instanceof Response) {
            response = (Response) o;
        } else if (o instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) o;
            if (MapUtils.isNotEmpty(map)) {
                Integer code = StringUtil.toInteger(map.get("status"));
                String message = String.format("%s:%s", map.get("path"), map.get("error"));
                response.failure(message, code);
            }
        } else {
            response.success(o);
        }
        return response;
    }
}
