package top.zopx.goku.framework.web.configurator;

import com.google.gson.Gson;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:06
 */
@Configuration
public class OkHttpFeignConfigurator {

    @Bean
    public OkHttpClient.Builder okHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Bean
    public GsonDecoder gsonDecoder(Gson gson) {
        return new GsonDecoder(gson);
    }

    @Bean
    public GsonEncoder gsonEncoder(Gson gson) {
        return new GsonEncoder(gson);
    }
}
