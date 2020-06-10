package top.zopx.starter.upload.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.ArrayUtils;
import top.zopx.starter.upload.config.FastDfsProperties;
import top.zopx.starter.upload.entity.Result;
import top.zopx.starter.upload.entity.UploadFile;
import top.zopx.starter.upload.service.FileManageService;
import top.zopx.starter.upload.util.Dir;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * top.zopx.starter.upload.service.impl.FastDFSManageService
 *
 * @author sanq.Yan
 * @date 2020/5/12
 */
public class FastDFSManageService implements FileManageService {

    @Resource
    private FastDfsProperties.NginxProperties nginxProperties;
    @Resource
    private FastFileStorageClient fastFileStorageClient;

    @Override
    public Result uploadFile(UploadFile uploadFile) {

        String path = Dir.get() + "/" + uploadFile.getRemoteFileName();

        StorePath storePath = fastFileStorageClient.uploadFile(
                new ByteArrayInputStream(uploadFile.getBytes()),
                uploadFile.getFileSize(),
                path,
                null
        );

        return Result.builder()
                .uploadFileUrl(storePath.getFullPath())
                .showFileUrl(getUrl(storePath.getFullPath()))
                .build();
    }

    @Override
    public Result resumeUploadFile(UploadFile uploadFile) {
        return Result.builder().build();
    }

    @Override
    public Result multipartUploadFile(UploadFile uploadFile) {
        return Result.builder().build();
    }

    @Override
    public List<String> deleteFile(String... keys) {

        if (!ArrayUtils.isEmpty(keys)) {
            List<String> keyList = new ArrayList<>(keys.length);

            Arrays.stream(keys).forEach(key -> {
                fastFileStorageClient.deleteFile(key);
                keyList.add(key);
            });

            return keyList;
        }

        return Collections.emptyList();
    }

    private String getUrl(String key) {
        return nginxProperties.getPrefix() + "://" + nginxProperties.getHost() + ":" + nginxProperties.getPort() + "/" + key;
    }
}
