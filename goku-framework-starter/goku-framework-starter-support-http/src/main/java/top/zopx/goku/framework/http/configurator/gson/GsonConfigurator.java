package top.zopx.goku.framework.http.configurator.gson;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:55
 */
@Configuration
public class GsonConfigurator {

    public static final String WRITE_GSON = "writeGson";

    @Bean(WRITE_GSON)
    @ConditionalOnMissingBean(name = WRITE_GSON)
    public Gson writeGson() {
        return GsonUtil.getInstance().getGson();
    }
}
