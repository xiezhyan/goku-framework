package top.zopx.goku.framework.web.filter;

import org.springframework.web.multipart.MultipartResolver;
import top.zopx.goku.framework.web.context.SpringContext;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/26
 */
public class ServletWrapperFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ResponseWrapper responseWrapper = new ResponseWrapper(httpServletResponse);

        String contentType = httpServletRequest.getContentType();
        if (Objects.isNull(contentType) || Objects.equals(contentType, "application/json")) {
            RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
            chain.doFilter(requestWrapper, responseWrapper);
        } else {
            chain.doFilter(SpringContext.getBean(MultipartResolver.class).resolveMultipart(httpServletRequest), responseWrapper);
        }

        byte[] content = responseWrapper.getContent();
        if (content.length > 0) {
            // 将最终结果写出到客户端
            try(ServletOutputStream outputStream = response.getOutputStream()) {
                outputStream.write(content);
                outputStream.flush();
            }
        }
    }
}
