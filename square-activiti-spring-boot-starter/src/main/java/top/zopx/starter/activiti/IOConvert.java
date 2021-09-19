package top.zopx.starter.activiti;

import org.springframework.util.Base64Utils;
import top.zopx.starter.tools.digest.base64.Base64Util;

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
        data = Base64Utils.encodeToString(bytes);
        inputStream.close();
        return data;
    }

    public InputStream base64ToInput(String data) throws IOException {
        byte[] bytes = Base64Util.INSTANCE.decode(data);
        return new ByteArrayInputStream(bytes);
    }
}
