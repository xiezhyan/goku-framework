package top.zopx.goku.framework.socket.tools.reader;

import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/9/12 21:54
 */
public class JsonReadStrategy implements IReadStrategy {

    @Override
    public Map<String, Object> read(String conf) {
        try (BufferedReader br = new BufferedReader(new FileReader(conf))) {
            String text = br.lines().collect(Collectors.joining());
            return GsonUtil.getInstance().toMap(text);
        } catch (Exception e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }
}
