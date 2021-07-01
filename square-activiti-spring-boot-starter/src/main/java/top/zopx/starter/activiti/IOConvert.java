package top.zopx.starter.activiti;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2021/7/1
 */
public enum IOConvert {
    /**
     * 单例
     */
    INSTANCE,
    ;

    public String inputToString(InputStream inputStream) throws IOException {
        String data = null;
        // in.available()返回文件的字节长度
        byte[] bytes = new byte[inputStream.available()];
        // 将文件中的内容读入到数组中
        int read = inputStream.read(bytes);
        data = new BASE64Encoder().encode(bytes);
        inputStream.close();
        return data;
    }

    public InputStream base64ToInput(String data) throws IOException {
        byte[] bytes = new BASE64Decoder().decodeBuffer(data);
        InputStream in = new ByteArrayInputStream(bytes);
        return in;
    }
}
