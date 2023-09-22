package top.zopx.goku.framework.socket.core.util;

import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:59
 */
public final class ReadFileUtil {

    private ReadFileUtil() {}

    public static <T> T read(String filePath, Class<T> clazz) {
        if (StringUtils.isBlank(filePath)) {
            throw new BusException("传入的配置文件为空", IBus.ERROR_CODE);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // 获取 JSON 文本
            String jsonText = br.lines().collect(Collectors.joining());
            // 解析 JSON 对象
            return GsonUtil.getInstance().getGson().fromJson(jsonText, clazz);
        } catch (Exception e) {
            throw new BusException("处理配置文件异常", IBus.ERROR_CODE);
        }
    }
}
