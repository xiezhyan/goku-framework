package top.zopx.starter.step.up.utils;

import freemarker.template.Template;
import org.springframework.util.CollectionUtils;
import top.zopx.starter.step.up.config.OverrideProperties;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * top.zopx.starter.step.up.utils.S
 *
 * @author sanq.Yan
 * @date 2020/4/28
 */
public class S {

    public static String replaceChar(String strVal, String tag) {
        StringBuffer sb = new StringBuffer();
        sb.append(strVal.toLowerCase());
        int count = sb.indexOf(tag);
        while (count != 0) {
            int num = sb.indexOf(tag, count);
            count = num + 1;
            if (num != -1) {
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count, count + 1, ia + "");
            }
        }
        return sb.toString().replaceAll(tag, "");
    }

    // 首字母大写
    public static String firstUpperCase(String strVal) {
        StringBuffer sb = new StringBuffer();
        if (null != strVal && !"".equals(strVal)) {
            sb.append(String.valueOf(strVal.charAt(0)).toUpperCase());
            for (int i = 1; i < strVal.length(); i++) {
                sb.append(strVal.charAt(i));
            }
        }
        return sb.toString();
    }

    public static String getFilePath(String basePath, String path) {
        return basePath + File.separator + path;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createFile(String path, String fileName, OverrideProperties overrided, String tableName) {
        File file = new File(getFilePath(path, fileName));


        boolean isOverride = CollectionUtils.isEmpty(overrided.getTableList()) ?
                                overrided.getOverrided() :
                                (overrided.getTableList().contains(tableName) && overrided.getOverrided());


        if (!file.exists() || isOverride) {

            new File(path).mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }
        return null;
    }

    public static void write(Template template, String path, File file, Map<String, Object> map) throws Exception {
        FileWriter fileWriter = new FileWriter(file);
        template.process(map, fileWriter);
    }
}
