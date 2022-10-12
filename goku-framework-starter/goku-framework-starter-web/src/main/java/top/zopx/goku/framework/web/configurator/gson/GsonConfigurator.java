package top.zopx.goku.framework.web.configurator.gson;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zopx.goku.framework.tools.util.json.IJson;
import top.zopx.goku.framework.tools.util.json.JsonUtil;
import top.zopx.goku.framework.tools.util.json.impl.GJson;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:55
 */
@Configuration
public class GsonConfigurator {

    @Bean
    public Gson writeGson() {
        return JsonUtil.getInstance().getGson();
    }

    @Bean
    public IJson json(Gson writeGson) {
        return new GJson(writeGson);
    }
}
