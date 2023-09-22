package top.zopx.goku.framework.cloud.configure.feign;

import com.google.gson.Gson;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author 俗世游子
 * @date 2022/2/26 0026
 * @email xiezhyan@126.com
 */
@Configuration
public class FeignConfigure {

    @Bean
    public GsonDecoder gsonDecoder(Gson writeGson) {
        return new GsonDecoder(writeGson);
    }

    @Bean
    public GsonEncoder gsonEncoder(Gson writeGson) {
        return new GsonEncoder(writeGson);
    }
}
