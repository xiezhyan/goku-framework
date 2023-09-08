package top.zopx.goku.framework.oss.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.oss.constant.OssErrorEnum;
import top.zopx.goku.framework.oss.service.OSSTemplate;
import top.zopx.goku.framework.tools.exception.BusException;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/22
 */
public class OSSTemplateImpl implements OSSTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(OSSTemplate.class);

    private final AmazonS3 amazonS3;

    public OSSTemplateImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public Boolean createBucket(String bucketName) {
        return createBucket(new CreateBucketRequest(bucketName));
    }

    @Override
    public Boolean createBucket(CreateBucketRequest request) {
        try {
            amazonS3.createBucket(request);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.CREATE_BUCKET_ERROR);
    }

    @Override
    public void setBucketAcl(String bucketName, CannedAccessControlList acl) {
        try {
            amazonS3.setBucketAcl(bucketName, acl);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.ACL_BUCKET_ERROR);
    }

    @Override
    public Boolean deleteBucket(String bucketName) {
        try {
            amazonS3.deleteBucket(bucketName);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.DELETE_BUCKET_ERROR);
    }

    @Override
    public Boolean putObject(String bucketName, String key, File file) {
        return putObject(new PutObjectRequest(bucketName, key, file));
    }

    @Override
    public Boolean putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) {
        return putObject(new PutObjectRequest(bucketName, key, input, metadata));
    }

    @Override
    public Boolean putObject(String bucketName, String key, String content) {
        try {
            amazonS3.putObject(bucketName, key, content);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.PUT_OBJECT_ERROR);
    }

    @Override
    public Boolean putObject(PutObjectRequest request) {
        try {
            amazonS3.putObject(request);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.PUT_OBJECT_ERROR);
    }

    @Override
    public Boolean copyObject(String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey) {
        try {
            amazonS3.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.COPY_OBJECT_ERROR);
    }

    @Override
    public S3Object getObject(String bucketName, String key) {
        return getObject(new GetObjectRequest(bucketName, key));
    }

    @Override
    public S3Object getObject(GetObjectRequest request) {
        try {
            return amazonS3.getObject(request);
        } catch (Exception e) {
            throw new BusException(OssErrorEnum.GET_OBJECT_ERROR);
        }
    }

    @Override
    public ObjectMetadata getObjectMetadata(String bucketName, String key) {
        try {
            return amazonS3.getObjectMetadata(bucketName, key);
        } catch (Exception e) {
            throw new BusException(OssErrorEnum.GET_OBJECT_METADATA_ERROR);
        }
    }

    @Override
    public Boolean deleteObject(String bucketName, String key) {
        return deleteObject(new DeleteObjectRequest(bucketName, key));
    }

    @Override
    public Boolean deleteObject(DeleteObjectRequest request) {
        try {
            amazonS3.deleteObject(request);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.DELETE_OBJECT_ERROR);
    }

    @Override
    public List<String> deleteObject(String bucketName, List<String> keys) {
        try {
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName);
            request.setKeys(keys.stream().map(DeleteObjectsRequest.KeyVersion::new).toList());
            DeleteObjectsResult result = amazonS3.deleteObjects(
                    request
            );
            return result.getDeletedObjects().stream().map(DeleteObjectsResult.DeletedObject::getKey).toList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.DELETE_OBJECT_ERROR);
    }

    @Override
    public void setObjectAcl(String bucketName, String key, CannedAccessControlList acl) {
        try {
            amazonS3.setObjectAcl(bucketName, key, acl);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.ACL_OBJECT_ERROR);
    }

    @Override
    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request) {
        try {
            return amazonS3.initiateMultipartUpload(request);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.INITIAL_PART_ERROR);
    }

    @Override
    public UploadPartResult uploadPart(UploadPartRequest request) {
        try {
            return amazonS3.uploadPart(request);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.UPLOAD_PART_ERROR);
    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request) {
        try {
            return amazonS3.completeMultipartUpload(request);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.COMPLETE_PART_ERROR);
    }

    @Override
    public void abortMultipartUpload(AbortMultipartUploadRequest request) {
        try {
            amazonS3.abortMultipartUpload(request);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.ABORT_PART_ERROR);
    }

    @Override
    public PartListing listParts(ListPartsRequest request) {
        try {
            return amazonS3.listParts(request);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        throw new BusException(OssErrorEnum.LIST_PART_ERROR);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, long expireTime, ChronoUnit unit, HttpMethod method) {
        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest(bucketName, key, method)
                        .withExpiration(
                                Date.from(LocalDateTime.now().plus(expireTime, unit).atZone(ZoneId.systemDefault()).toInstant())
                        )
                        .withContentType("application/octet-stream");
        return amazonS3.generatePresignedUrl(request);
    }
}
