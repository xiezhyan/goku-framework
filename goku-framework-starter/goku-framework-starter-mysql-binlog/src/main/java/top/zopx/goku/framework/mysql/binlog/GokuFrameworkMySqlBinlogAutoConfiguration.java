package top.zopx.goku.framework.mysql.binlog;

import com.google.gson.Gson;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.mysql.binlog.entity.TemplateSchema;
import top.zopx.goku.framework.mysql.binlog.properties.BootstrapBinlog;
import top.zopx.goku.framework.mysql.binlog.template.ParseTemplate;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/24 22:24
 */
@Component
@ComponentScan("top.zopx.goku.framework.mysql.binlog")
public class GokuFrameworkMySqlBinlogAutoConfiguration {

    @Resource
    private BootstrapBinlog bootstrapBinlog;
    @Resource
    private Gson writeGson;

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
        LogHelper.getLogger(getClass()).info("{} parse data = {}", bootstrapBinlog.getTemplate(), schema.toString());
        ParseTemplate.parse(schema);
    }

}
