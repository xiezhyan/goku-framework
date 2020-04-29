package top.zopx.starter.step.up.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.util.StringUtils;
import top.zopx.starter.step.up.config.OverrideProperties;
import top.zopx.starter.step.up.config.StepUpProperties;
import top.zopx.starter.step.up.constant.TableData;
import top.zopx.starter.step.up.entity.Column;
import top.zopx.starter.step.up.entity.Table;
import top.zopx.starter.step.up.utils.S;
import top.zopx.starter.tools.tools.date.LocalDateUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * top.zopx.starter.step.up.service.StepupService
 *
 * @author sanq.Yan
 * @date 2020/4/28
 */
@Slf4j
public class StepupService {

    @Resource
    private FreeMarkerConfigurationFactory freeMarkerConfigurationFactory;
    @Resource
    private Configuration configuration;

    @Resource
    private StepUpProperties stepUpProperties;


    @SneakyThrows
    public void init() {

        configuration = freeMarkerConfigurationFactory.createConfiguration();

        String rootPath = stepUpProperties.getRootPath();

        Map<String, Object> map = new HashMap<>();

        String packageName = stepUpProperties.getPackageName();

        File rootFile = new File(rootPath + File.separator + "src" + File.separator + "main" + File.separator + "java");
        if (StringUtils.isEmpty(packageName)) {
            StringBuffer sb = new StringBuffer();
            // 获取src/main/java下的包路径
            getPackageByRootPath(rootFile, sb);

            packageName = sb.deleteCharAt(sb.length() - 1).toString().replace(File.separator, ".");
        }

        map.put("package", packageName);
        map.put("nowDate", LocalDateUtils.nowTime());
        map.put("type", stepUpProperties.getType().name().toLowerCase());

        // 总路径 连带项目名 + 包名
        String _rootPath = rootFile.getAbsolutePath() + File.separator + (packageName.replace(".", File.separator));

        for (Map.Entry<Table, List<Column>> entry : TableData.MAP.entrySet()) {
            map.put("table", entry.getKey());
            map.put("fields", entry.getValue());

            controllerTemplate(_rootPath, entry, map);

            entityTemplate(_rootPath, entry, map);

            entityVoTemplate(_rootPath, entry, map);

            serviceTemplate(_rootPath, entry, map);

            serviceImplTemplate(_rootPath, entry, map);

            mapperTemplate(_rootPath, entry, map);

            mapperXmlTemplate(entry, map);

            apiTemplate(entry, map);
        }
    }

    @SneakyThrows
    private void apiTemplate(Map.Entry<Table, List<Column>> entry, Map<String, Object> map) {
        File rootFile = new File(stepUpProperties.getRootPath() + File.separator);

        String _rootPath = rootFile.getAbsolutePath();

        Template template = configuration.getTemplate("views/api/Api.ftl");

        String path = S.getFilePath(_rootPath, "views" + File.separator + "api");

        String tableName = S.firstUpperCase(entry.getKey().getJavaName());

        File file = S.createFile(path, tableName + "Api.js", buildOver(), entry.getKey().getTableName());
        if (null != file) {
            S.write(template, path, file, map);
            log.info("{}: api 生成, path:{}", tableName, path);
        }
    }

    private OverrideProperties buildOver() {
        return OverrideProperties.builder()
                .overrided(stepUpProperties.getOverrided())
                .tableList(stepUpProperties.getTableList())
                .build();
    }

    @SneakyThrows
    private void mapperXmlTemplate(Map.Entry<Table, List<Column>> entry, Map<String, Object> map) {
        File rootFile = new File(stepUpProperties.getRootPath() + File.separator + "src" + File.separator + "main" + File.separator + "resources");

        String _rootPath = rootFile.getAbsolutePath();

        Template template = configuration.getTemplate("java/mapper_xml.ftl");

        String path = S.getFilePath(_rootPath, "mapper");

        String tableName = S.firstUpperCase(entry.getKey().getJavaName());

        File file = S.createFile(path, tableName + "Mapper.xml", buildOver(), entry.getKey().getTableName());
        if (null != file) {
            S.write(template, path, file, map);
            log.info("{}:mapper xml 生成, path:{}", tableName, path);
        }
    }

    @SneakyThrows
    private void mapperTemplate(String _rootPath, Map.Entry<Table, List<Column>> entry, Map<String, Object> map) {
        Template template = configuration.getTemplate("java/mapper.ftl");

        String path = S.getFilePath(_rootPath, "mapper");

        String tableName = S.firstUpperCase(entry.getKey().getJavaName());

        File file = S.createFile(path, tableName + "Mapper.java", buildOver(), entry.getKey().getTableName());
        if (null != file) {
            S.write(template, path, file, map);
            log.info("{}:mapper 生成, path:{}", tableName, path);
        }
    }

    @SneakyThrows
    private void serviceImplTemplate(String _rootPath, Map.Entry<Table, List<Column>> entry, Map<String, Object> map) {
        Template template = configuration.getTemplate("java/service_impl.ftl");

        String path = S.getFilePath(_rootPath, "service" + File.separator + "impl");

        String tableName = S.firstUpperCase(entry.getKey().getJavaName());

        File file = S.createFile(path, tableName + "ServiceImpl.java", buildOver(), entry.getKey().getTableName());
        if (null != file) {
            S.write(template, path, file, map);
            log.info("{}:serviceImpl 生成, path:{}", tableName, path);
        }
    }

    @SneakyThrows
    private void serviceTemplate(String _rootPath, Map.Entry<Table, List<Column>> entry, Map<String, Object> map) {
        Template template = configuration.getTemplate("java/service.ftl");

        String path = S.getFilePath(_rootPath, "service");

        String tableName = S.firstUpperCase(entry.getKey().getJavaName());

        File file = S.createFile(path, tableName + "Service.java", buildOver(), entry.getKey().getTableName());
        if (null != file) {
            S.write(template, path, file, map);
            log.info("{}:service 生成, path:{}", tableName, path);
        }
    }

    @SneakyThrows
    private void entityVoTemplate(String _rootPath, Map.Entry<Table, List<Column>> entry, Map<String, Object> map) {
        Template template = configuration.getTemplate("java/entityVo.ftl");

        String path = S.getFilePath(_rootPath, "entity" + File.separator + "vo");

        String tableName = S.firstUpperCase(entry.getKey().getJavaName());

        File file = S.createFile(path, tableName + "Vo.java", buildOver(), entry.getKey().getTableName());
        if (null != file) {
            S.write(template, path, file, map);
            log.info("{}:entityVo 生成, path:{}", tableName, path);
        }
    }

    @SneakyThrows
    private void entityTemplate(String _rootPath, Map.Entry<Table, List<Column>> entry, Map<String, Object> map) {
        Template template = configuration.getTemplate("java/entity.ftl");

        String path = S.getFilePath(_rootPath, "entity" + File.separator + "db");

        String tableName = S.firstUpperCase(entry.getKey().getJavaName());

        File file = S.createFile(path, tableName + ".java", buildOver(), entry.getKey().getTableName());
        if (null != file) {
            S.write(template, path, file, map);
            log.info("{}:entity 生成, path:{}", tableName, path);
        }
    }

    // controller
    @SneakyThrows
    private void controllerTemplate(String _rootPath, Map.Entry<Table, List<Column>> entry, Map<String, Object> map) {
        Template template = configuration.getTemplate("java/controller.ftl");

        String path = S.getFilePath(_rootPath, "controller");

        String tableName = S.firstUpperCase(entry.getKey().getJavaName());

        File file = S.createFile(path, tableName + "Controller.java", buildOver(), entry.getKey().getTableName());
        if (null != file) {
            S.write(template, path, file, map);
            log.info("{}:controller 生成, path:{}", tableName, path);
        }
    }

    private static void getPackageByRootPath(File file, StringBuffer sb) {
        File[] fileLists = file.listFiles(); // 如果是目录，获取该目录下的内容集合

        assert fileLists != null;
        for (File f : fileLists) {
            if (f.isDirectory()) {
                sb.append(f.getName()).append(File.separator);
                getPackageByRootPath(f, sb);
            }
        }
    }
}
