package top.zopx.starter.upload.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;
import top.zopx.starter.tools.tools.strings.StringUtil;

import java.io.InputStream;

/**
 * top.zopx.starter.upload.entity.UploadFile
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Data
@Builder
public class UploadFile {

    private String fileName;

    private long fileSize;

    private InputStream stream;

    public String getFileName() {

        if (StringUtils.isEmpty(fileName))
            return StringUtil.uuid();

        String suffix = fileName.substring(fileName.lastIndexOf("."));

        return StringUtil.uuid() + suffix;
    }
}
