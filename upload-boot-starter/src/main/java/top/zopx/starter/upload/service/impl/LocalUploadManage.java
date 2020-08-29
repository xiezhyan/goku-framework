package top.zopx.starter.upload.service.impl;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import top.zopx.starter.upload.config.UploadProperties;
import top.zopx.starter.upload.entity.Result;
import top.zopx.starter.upload.entity.UploadFile;
import top.zopx.starter.upload.service.FileManageService;
import top.zopx.starter.upload.util.Dir;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * top.zopx.starter.upload.service.impl.LocalUploadManage
 *
 * @author sanq.Yan
 * @date 2020/8/29
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
public class LocalUploadManage implements FileManageService {

    @Resource
    private UploadProperties.LocalProperties localProperties;

    @SneakyThrows
    @Override
    public Result uploadFile(UploadFile uploadFile) {
        if (null == uploadFile)
            return Result.builder().build();

        String rootPath = localProperties.getLocalDir() + File.separator + Dir.get();
        File rootFile = new File(rootPath);

        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }

        String filePath = rootPath + File.separator + uploadFile.getRemoteFileName();
        File file = new File(filePath);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileUtils.writeByteArrayToFile(file, uploadFile.getBytes());

        return Result.builder()
                .uploadFileUrl(Dir.get() + File.separator + file.getName())
                .showFileUrl(getUrl(Dir.get() + File.separator + file.getName()))
                .build();
    }

    @Override
    public List<String> deleteFile(String... keys) {
        if (!ArrayUtils.isEmpty(keys)) {
            List<String> keyList = new ArrayList<>(keys.length);

            Arrays.stream(keys).forEach(key -> {
                File file = new File(localProperties.getLocalDir() + File.separator + key);

                if (file != null) {
                    file.delete();
                    keyList.add(key);
                }
            });

            return keyList;
        }

        return Collections.emptyList();
    }

    @Override
    public Result resumeUploadFile(UploadFile uploadFile) {
        return null;
    }

    @Override
    public Result multipartUploadFile(UploadFile uploadFile) {
        return null;
    }

    private String getUrl(String key) {
        return localProperties.getShowImgUrlPrefix() + "/" + key;
    }
}
