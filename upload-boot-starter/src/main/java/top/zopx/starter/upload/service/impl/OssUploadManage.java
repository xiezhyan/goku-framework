package top.zopx.starter.upload.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;
import top.zopx.starter.upload.config.UploadProperties;
import top.zopx.starter.upload.entity.Result;
import top.zopx.starter.upload.entity.UploadFile;
import top.zopx.starter.upload.service.FileManageService;
import top.zopx.starter.upload.util.Dir;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.*;

/**
 * top.zopx.starter.upload.service.impl.OssUploadManage
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Slf4j
public class OssUploadManage implements FileManageService {

    @Resource
    private OSS oss;
    @Resource
    private UploadProperties.OssProperties ossProperties;

    @Override
    public Result uploadFile(UploadFile uploadFile) {
        return uploadFile(Collections.singletonList(uploadFile)).get(0);
    }

    /**
     * 简单上传
     *
     * @param uploadFiles 上传文件集合
     * @return 文件上传链接集合
     */
    @Override
    public List<Result> uploadFile(List<UploadFile> uploadFiles) {
        if (CollectionUtils.isEmpty(uploadFiles))
            return Collections.emptyList();

        List<Result> resultList = new ArrayList<>(uploadFiles.size());

        uploadFiles.forEach(uploadFile -> {
            String path = Dir.get() + "/" + uploadFile.getRemoteFileName();

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    ossProperties.getBucketName(),
                    path,
                    new ByteArrayInputStream(uploadFile.getBytes()));

            oss.putObject(putObjectRequest);

            resultList.add(
                    Result.builder()
                            .showFileUrl(getUrl(path))
                            .uploadFileUrl(path)
                            .build()
            );
        });

        return resultList;
    }

    @Override
    public List<String> deleteFile(String... keys) {
        if (!ArrayUtils.isEmpty(keys)) {
            List<String> keyList = new ArrayList<>(keys.length);

            keyList.addAll(Arrays.asList(keys));

            DeleteObjectsResult deleteObjectsResult = oss.deleteObjects(new DeleteObjectsRequest(ossProperties.getBucketName()).withKeys(keyList));

            return deleteObjectsResult.getDeletedObjects();
        }
        return Collections.emptyList();
    }

    @Override
    public Result resumeUploadFile(UploadFile uploadFile) {
        return resumeUploadFile(Collections.singletonList(uploadFile)).get(0);
    }

    @Override
    public List<Result> resumeUploadFile(List<UploadFile> uploadFiles) {
        if (CollectionUtils.isEmpty(uploadFiles))
            return Collections.emptyList();

        List<Result> resultList = new ArrayList<>(uploadFiles.size());

        uploadFiles.forEach(uploadFile -> {
            String path = Dir.get() + "/" + uploadFile.getRemoteFileName();

            // 上传文件
            UploadFileRequest uploadFileRequest = new UploadFileRequest(ossProperties.getBucketName(), path);
            // 需要上传的本地文件
            uploadFileRequest.setUploadFile(uploadFile.getLocalFilePath());
            // 上传并发线程
            uploadFileRequest.setTaskNum(5);
            // 上传分片大小
            uploadFileRequest.setPartSize(1024 * 1024 * 3);
            // 开启断点续传
            uploadFileRequest.setEnableCheckpoint(true);

            try {
                oss.uploadFile(uploadFileRequest);

                resultList.add(
                        Result.builder()
                                .showFileUrl(getUrl(path))
                                .uploadFileUrl(path)
                                .build()
                );
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        return resultList;
    }

    private String getUrl(String key) {
        if (null != ossProperties.getPresign()) {
            if (ossProperties.getPresign().getPresigned()) {
                // 设置加密
                Date expiration = new Date(new Date().getTime() + ossProperties.getPresign().getPresignTime() * 1000);

                // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
                URL url = this.oss.generatePresignedUrl(ossProperties.getBucketName(), key, expiration);
                return url.toString();
            }
        }
        return ossProperties.getShowImgUrlPrefix() + "/" + key;
    }
}
