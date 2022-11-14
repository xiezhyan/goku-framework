package top.zopx.goku.example.socket.common.util;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.util.json.JsonUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:59
 */
public final class ReadFileUtil {

    private ReadFileUtil() {}

    public static JsonObject read(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            throw new BusException("传入的配置文件为空");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // 获取 JSON 文本
            String jsonText = br.lines().collect(Collectors.joining());
            // 解析 JSON 对象
            return JsonUtil.getInstance().getGson().fromJson(jsonText, JsonObject.class);
        } catch (Exception e) {
            throw new BusException("处理配置文件异常");
        }
    }
}
