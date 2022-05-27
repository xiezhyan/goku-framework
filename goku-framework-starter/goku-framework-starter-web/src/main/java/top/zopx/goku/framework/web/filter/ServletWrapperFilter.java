package top.zopx.goku.framework.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        ResponseWrapper responseWrapper = new ResponseWrapper(httpServletResponse);

        chain.doFilter(requestWrapper, responseWrapper);

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
