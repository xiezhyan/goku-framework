package top.zopx.goku.framework.web.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/26
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream buff;
    private final ServletOutputStream out;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        buff = new ByteArrayOutputStream();
        out = new WrapperOutputStream(buff);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (Objects.nonNull(out)) {
            out.flush();
        }
    }

    public byte[] getContent() {
        try {
            flushBuffer();
            return buff.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private static class WrapperOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream buff;

        public WrapperOutputStream(ByteArrayOutputStream buff) {
            this.buff = buff;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            // TODO document why this method is empty
        }

        @Override
        public void write(int b) throws IOException {
            buff.write(b);
        }
    }
}
