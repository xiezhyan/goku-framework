package top.zopx.goku.framework.socket.tools.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/9/12 21:55
 */
public class YamlReadStrategy implements IReadStrategy {
    // Jackson
    @Override
    public Map<String, Object> read(String conf) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(new File(conf), Map.class);
        } catch (IOException e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }
}
