package top.zopx.goku.framework.support.mysql.binlog.runner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.support.mysql.binlog.entity.Template;
import top.zopx.goku.framework.support.mysql.binlog.properties.BootstrapBinlog;
import top.zopx.goku.framework.support.mysql.binlog.template.ParseTemplate;
import top.zopx.goku.framework.tools.exception.BusException;

import jakarta.annotation.Resource;
import top.zopx.goku.framework.tools.exception.IBus;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Mr.Xie
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
            // 全路径
            try {
                inputStream = new FileInputStream(template);
            } catch (Exception e) {
                throw new BusException(e.getMessage(), IBus.ERROR_CODE);
            }
        }
        List<Template> templates =
                writeGson.fromJson(new BufferedReader(new InputStreamReader(inputStream)), new TypeToken<List<Template>>() {
                }.getType());
        LOGGER.info("{} parse data = {}", bootstrapBinlog.getTemplate(), templates);
        ParseTemplate.parse(templates);
    }
}
