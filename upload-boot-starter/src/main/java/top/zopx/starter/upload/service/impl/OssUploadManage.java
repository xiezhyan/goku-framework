package top.zopx.starter.upload.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;
import top.zopx.starter.upload.config.UploadProperties;
import top.zopx.starter.upload.entity.Result;
import top.zopx.starter.upload.entity.UploadFile;
import top.zopx.starter.upload.service.FileManageService;
import top.zopx.starter.upload.util.Async;
import top.zopx.starter.upload.util.Dir;

import javax.annotation.Resource;
import java.net.URL;
import java.util.*;

/**
 * top.zopx.starter.upload.service.impl.OssUploadManage
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
public class OssUploadManage implements FileManageService {

    @Resource
    private OSS oss;
    @Resource
    private UploadProperties.OssProperties ossProperties;

    @Override
    public Result uploadFile(UploadFile uploadFile) {
        return uploadFile(Collections.singletonList(uploadFile)).get(0);
    }

    @Override
    public List<Result> uploadFile(List<UploadFile> uploadFiles) {
        if (CollectionUtils.isEmpty(uploadFiles))
            return Collections.emptyList();

        List<Result> resultList = new ArrayList<>(uploadFiles.size());

        uploadFiles.forEach(uploadFile -> {
            String path = Dir.get() + "/" + uploadFile.getFileName();

            PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), path, uploadFile.getStream());

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
