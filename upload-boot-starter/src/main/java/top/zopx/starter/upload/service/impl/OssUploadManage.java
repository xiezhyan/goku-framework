package top.zopx.starter.upload.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.upload.config.UploadProperties;
import top.zopx.starter.upload.entity.Result;
import top.zopx.starter.upload.entity.UploadFile;
import top.zopx.starter.upload.service.FileManageService;
import top.zopx.starter.upload.util.Dir;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);
    private static final List<PartETag> PART_E_TAGS = Collections.synchronizedList(new ArrayList<>());
    private static final long PART_SIZE = 5L * 1024 * 1024;

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

    @Override
    public Result multipartUploadFile(UploadFile uploadFile) {
        return multipartUploadFile(Collections.singletonList(uploadFile)).get(0);
    }

    @Override
    public List<Result> multipartUploadFile(List<UploadFile> uploadFiles) {
        if (CollectionUtils.isEmpty(uploadFiles))
            return Collections.emptyList();

        List<Result> resultList = new ArrayList<>(uploadFiles.size());

        uploadFiles.forEach(uploadFile -> {
            String path = Dir.get() + "/" + uploadFile.getRemoteFileName();

            // 分片对象
            String uploadId = createUploadId(path);
            log.debug("uploadId: {}", uploadId);

            // 计算分片数量
            long partCount = uploadFile.getFileSize() / PART_SIZE;
            if (uploadFile.getFileSize() % PART_SIZE != 0)
                partCount++;

            log.debug("Total parts count：{}", partCount);
            if (partCount > 10000) {
                throw new BusException("Total parts count should not exceed 10000");
            }

            for (int i = 0; i < partCount; i++) {
                long startPos = i * PART_SIZE;
                long curPartSize = (i + 1 == partCount) ? (uploadFile.getFileSize() - startPos) : PART_SIZE;

                EXECUTOR_SERVICE.execute(
                        new PartUploader(
                                uploadFile.getBytes(),
                                startPos,
                                curPartSize,
                                i + 1,
                                uploadId,
                                path)
                );
            }

            EXECUTOR_SERVICE.shutdown();
            while (!EXECUTOR_SERVICE.isTerminated()) {
                try {
                    EXECUTOR_SERVICE.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 验证
            if (PART_E_TAGS.size() != partCount) {
                throw new BusException("Upload multiparts fail due to some parts are not finished yet");
            }

            // 创建CompleteMultipartUploadRequest对象。
            // 在执行完成分片上传操作时，需要提供所有有效的partETags。
            // OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
            completeMultipartUpload(uploadId, path);

            PART_E_TAGS.clear();

            resultList.add(
                    Result.builder()
                            .showFileUrl(getUrl(path))
                            .uploadFileUrl(path)
                            .uploadId(uploadId)
                            .build()
            );
        });
        return resultList;
    }

    private void completeMultipartUpload(String uploadId, String key) {
        PART_E_TAGS.sort(Comparator.comparingInt(PartETag::getPartNumber));

        log.debug("Completing to upload multiparts");
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(ossProperties.getBucketName(), key, uploadId, PART_E_TAGS);
        oss.completeMultipartUpload(completeMultipartUploadRequest);
    }

    private String createUploadId(String path) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(ossProperties.getBucketName(), path);
        InitiateMultipartUploadResult result = oss.initiateMultipartUpload(request);
        return result.getUploadId();
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

    private class PartUploader implements Runnable {

        private byte[] bytes;
        private long startPos;
        private long curPartSize;
        private int index;
        private String uploadId;
        private String key;

        PartUploader(byte[] bytes, long startPos, long curPartSize, int index, String uploadId, String key) {
            this.bytes = bytes;
            this.startPos = startPos;
            this.curPartSize = curPartSize;
            this.index = index;
            this.uploadId = uploadId;
            this.key = key;
        }

        @Override
        public void run() {
            InputStream instream = null;
            try {
                instream = new ByteArrayInputStream(bytes);
                long skip = instream.skip(this.startPos);
                log.debug("skip {} start", skip);

                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(ossProperties.getBucketName());
                uploadPartRequest.setKey(key);
                uploadPartRequest.setUploadId(this.uploadId);
                uploadPartRequest.setInputStream(instream);
                uploadPartRequest.setPartSize(curPartSize);
                uploadPartRequest.setPartNumber(index);

                UploadPartResult uploadPartResult = oss.uploadPart(uploadPartRequest);
                log.debug("Part# {} done", this.index);

                synchronized (PART_E_TAGS) {
                    PART_E_TAGS.add(uploadPartResult.getPartETag());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (instream != null) {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
