package top.zopx.starter.upload.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import top.zopx.starter.tools.tools.strings.StringUtil;

import java.io.File;

/**
 * top.zopx.starter.upload.entity.UploadFile
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadFile {

    /**
     * 上传文件本地路径
     */
    private String localFilePath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private long fileSize;

    /**
     * 文件二进制数组
     */
    private byte[] bytes;

    /**
     * 是否重命名
     */
    private boolean isRename = false;

    public String getRemoteFileName() {

        if (StringUtils.isEmpty(fileName))
            return StringUtil.uuid();

        if (isRename) {
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            return StringUtil.uuid() + suffix;
        }
        return fileName;
    }
}
