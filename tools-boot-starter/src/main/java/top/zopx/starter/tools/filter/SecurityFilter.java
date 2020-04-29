package top.zopx.starter.tools.filter;

import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import top.zopx.starter.tools.tools.web.ParamUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * com.sanq.product.security.filters.SecurityFilter
 *
 * @author sanq.Yan
 * @date 2019/8/3
 */
public class SecurityFilter implements Filter {

    public static final String EXCLUSION_KEY = "exclusion";

    private List<String> exclusions;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exclusion = filterConfig.getInitParameter(EXCLUSION_KEY);

        if (StringUtils.isEmpty(exclusion))
            exclusions = Collections.emptyList();
        else
            exclusions = Arrays.asList(exclusion.split(","));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityHttpServletRequestWrapper wrap = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;

            if (!"get".equalsIgnoreCase(httpServletRequest.getMethod().toUpperCase())
                    && !exclusions.contains(httpServletRequest.getServletPath())) {
                wrap = new SecurityHttpServletRequestWrapper(httpServletRequest);
                ParamUtil.getInstance().set(wrap.getJson());
            }
        }
        if (null != wrap) {
            chain.doFilter(wrap, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}

class SecurityHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private String json;

    SecurityHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        json = StreamUtils.copyToString(this.getRequest().getInputStream(), StandardCharsets.UTF_8);
    }

    @Override
    public ServletInputStream getInputStream() {
        byte[] buffer = null;
        buffer = json.getBytes(StandardCharsets.UTF_8);
        final ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }

        };
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

}