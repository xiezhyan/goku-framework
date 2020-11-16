package top.zopx.starter.tools.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.zopx.starter.tools.annotations.PassResponseAdviceAnnotation;
import top.zopx.starter.tools.basic.Response;
import top.zopx.starter.tools.exceptions.BusException;

/**
 * @author sanq.Yan
 * @date 2020/8/11
 */
@RestControllerAdvice
@SuppressWarnings("all")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

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
        } else {
            response.success(o);
        }
        return response;
    }
}
