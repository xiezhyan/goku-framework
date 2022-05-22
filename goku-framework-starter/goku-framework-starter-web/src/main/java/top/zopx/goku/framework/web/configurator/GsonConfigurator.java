package top.zopx.goku.framework.web.configurator;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import top.zopx.goku.framework.tools.util.json.IJson;
import top.zopx.goku.framework.tools.util.json.JsonUtil;
import top.zopx.goku.framework.tools.util.json.impl.GJson;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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

    @Bean
    public GsonHttpMessageConverter gsonHttpMessageConverter(Gson writeGson) {
        GsonHttpMessageConverter gsonHttpConverter = new GsonHttpMessageConverter(writeGson);
        gsonHttpConverter.setDefaultCharset(Charset.defaultCharset());

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.ALL);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        gsonHttpConverter.setSupportedMediaTypes(supportedMediaTypes);

        return gsonHttpConverter;
    }
}
