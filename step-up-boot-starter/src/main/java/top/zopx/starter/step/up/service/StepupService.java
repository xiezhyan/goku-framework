package top.zopx.starter.step.up.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.util.StringUtils;
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
public class StepupService {

    @Resource
    private FreeMarkerConfigurationFactory freeMarkerConfigurationFactory;

    @Resource
    private StepUpProperties stepUpProperties;

    @SneakyThrows
    public void init() {
        Configuration configuration = freeMarkerConfigurationFactory.createConfiguration();

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

        for (Map.Entry<Table, List<Column>> entry : TableData.MAP.entrySet()) {
            map.put("table", entry.getKey());
            map.put("fields", entry.getValue());

            Template template = configuration.getTemplate("java/controller.ftl");

            String _rootPath = rootPath + File.separator + (packageName.replace(".", File.separator));
            String path = S.getFilePath(_rootPath, "controller");

            String tableName = S.firstUpperCase(entry.getKey().getJavaName());

            File file = S.createFile(path, tableName + "Controller.java");
            S.write(template, path, file, map);
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
