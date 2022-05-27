package top.zopx.goku.framework.web.filter;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import top.zopx.goku.framework.tools.util.json.JsonUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/26
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private final byte[] buff;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        InputStream is = request.getInputStream();
        buff = IOUtils.toByteArray(is);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(buff);
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
            public void setReadListener(ReadListener listener) {
                // TODO document why this method is empty
            }
            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public byte[] getBuff() {
        if(HttpMethod.GET.matches(getMethod())) {
            return JsonUtil.getInstance().getJson().toJson(getParameterMap()).getBytes(StandardCharsets.UTF_8);
        }
        return buff;
    }
}
