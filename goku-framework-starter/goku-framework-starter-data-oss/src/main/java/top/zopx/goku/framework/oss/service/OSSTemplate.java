package top.zopx.goku.framework.oss.service;

import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/22
 */
public interface OSSTemplate {

    /**
     * 创建Bucket
     *
     * @param bucketName bucketName
     * @return Boolean
     */
    Boolean createBucket(String bucketName);

    /**
     * 创建bucket
     *
     * @param request request
     * @return Boolean
     */
    Boolean createBucket(CreateBucketRequest request);

    /**
     * bucket acl
     *
     * @param bucketName bucketName
     * @param acl        acl
     */
    void setBucketAcl(String bucketName, CannedAccessControlList acl);

    /**
     * 删除Bucket
     *
     * @param bucketName bucketName
     * @return Boolean
     */
    Boolean deleteBucket(String bucketName);

    /**
     * @param bucketName bucketName
     * @param key        key
     * @param file       file
     * @return Boolean
     */
    Boolean putObject(String bucketName, String key, File file);

    /**
     * 上传文件
     *
     * @param bucketName bucketName
     * @param key        key
     * @param input      input
     * @param metadata   metadata
     * @return Boolean
     */
    Boolean putObject(
            String bucketName, String key, InputStream input, ObjectMetadata metadata);

    /**
     * 上传文件
     *
     * @param bucketName bucketName
     * @param key        key
     * @param content    content
     * @return Boolean
     */
    Boolean putObject(String bucketName, String key, String content);

    /**
     * 上传文件
     *
     * @param request request
     * @return Boolean
     */
    Boolean putObject(PutObjectRequest request);

    /**
     * 拷贝文件
     *
     * @param sourceBucketName      sourceBucketName
     * @param sourceKey             sourceKey
     * @param destinationBucketName destinationBucketName
     * @param destinationKey        destinationKey
     * @return CopyObjectResult
     */
    Boolean copyObject(String sourceBucketName, String sourceKey,
                                String destinationBucketName, String destinationKey);


    /**
     * 查看指定对象
     *
     * @param bucketName bucketName
     * @param key        key
     * @return S3Object
     */
    S3Object getObject(String bucketName, String key);

    /**
     * 获取对象元数据
     *
     * @param bucketName bucketName
     * @param key        key
     * @return ObjectMetadata
     */
    ObjectMetadata getObjectMetadata(String bucketName, String key);

    /**
     * 查看指定对象
     *
     * @param request request
     * @return S3Object
     */
    S3Object getObject(GetObjectRequest request);

    /**
     * 删除对象
     *
     * @param bucketName bucketName
     * @param key        对象Key
     * @return Boolean
     */
    Boolean deleteObject(String bucketName, String key);

    /**
     * 删除对象
     *
     * @param request request
     * @return Boolean
     */
    Boolean deleteObject(DeleteObjectRequest request);

    /**
     * 批量删除
     *
     * @param bucketName bucketName
     * @param keys       keys
     * @return 被删除对象
     */
    List<String> deleteObject(String bucketName, List<String> keys);

    /**
     * object acl
     *
     * @param bucketName bucketName
     * @param key        key
     * @param acl        acl
     */
    void setObjectAcl(String bucketName, String key, CannedAccessControlList acl);

    /**
     * 初始化分片上传
     *
     * @param request request
     * @return InitiateMultipartUploadResult
     */
    InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request);

    /**
     * 分片上传
     *
     * @param request request
     * @return UploadPartResult
     */
    UploadPartResult uploadPart(UploadPartRequest request);

    /**
     * 完成分片上传
     *
     * @param request request
     * @return CompleteMultipartUploadResult
     */
    CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request);

    /**
     * 取消分片上传
     *
     * @param request request
     */
    void abortMultipartUpload(AbortMultipartUploadRequest request);

    /**
     * 列举已上传的分片
     *
     * @param request request
     * @return PartListing
     */
    PartListing listParts(ListPartsRequest request);
}
