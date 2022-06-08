package top.zopx.goku.framework.mysql.binlog.runner;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.mysql.binlog.entity.TemplateSchema;
import top.zopx.goku.framework.mysql.binlog.properties.BootstrapBinlog;
import top.zopx.goku.framework.mysql.binlog.template.ParseTemplate;
import top.zopx.goku.framework.tools.exceptions.BusException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/24 22:50
 */
@Component
public class LoadingTemplate {

    @Resource
    private BootstrapBinlog bootstrapBinlog;
    @Resource
    private Gson writeGson;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadingTemplate.class);

    @PostConstruct
    public void init() {
        load(bootstrapBinlog.getTemplate());
    }

    private void load(String template) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = cl.getResourceAsStream(template);
        if (Objects.isNull(inputStream)) {
            throw new BusException("fail load template.json");
        }
        final TemplateSchema schema = writeGson.fromJson(new BufferedReader(new InputStreamReader(inputStream)), TemplateSchema.class);
        LOGGER.info("{} parse data = {}", bootstrapBinlog.getTemplate(), schema.toString());
        ParseTemplate.parse(schema);
    }
}
