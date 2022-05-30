package top.zopx.goku.framework.material.configurator.minio.service;

import io.minio.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import top.zopx.goku.framework.material.configurator.minio.client.MinIOClientConfigurator;
import top.zopx.goku.framework.material.constant.MaterialPolicy;
import top.zopx.goku.framework.material.entity.MaterialBucket;
import top.zopx.goku.framework.material.entity.check.BucketName;
import top.zopx.goku.framework.material.entity.check.Region;
import top.zopx.goku.framework.material.service.IMaterialService;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:31
 */
@Service("minio")
@ConditionalOnBean(MinIOClientConfigurator.MinIOMarker.class)
public class MinioServiceImpl implements IMaterialService {

    @Resource
    private MinioClient writeMinioClient;

    @Override
    public boolean existsBucket(BucketName bucketName, Region region) {
        try {
            final BucketExistsArgs.Builder bucket = BucketExistsArgs.builder()
                    .bucket(bucketName.getName());

            if (Objects.nonNull(region)) {
                bucket.region(region.getRegion());
            }

            return writeMinioClient.bucketExists(
                    bucket.build()
            );
        } catch (Exception e) {
            LogHelper.getLogger(MinioServiceImpl.class).error(e.getMessage(), e);
            throw new BusException(e.getMessage());
        }
    }

    @Override
    public void createBucket(MaterialBucket bucket) {
        if (!existsBucket(bucket.getBucketName(), bucket.getRegion())) {
            throw new BusException("当前Bucket已存在");
        }

        final MakeBucketArgs.Builder makeBucket = MakeBucketArgs.builder().bucket(bucket.getBucketName().getName());
        final SetBucketPolicyArgs.Builder policyBucket = SetBucketPolicyArgs.builder()
                .bucket(bucket.getBucketName().getName())
                .config(getPolicy(bucket.getPolicy(), bucket.getBucketName().getName(), bucket.getCustomPolicyMsg()));

        if (Objects.nonNull(bucket.getRegion())) {
            makeBucket.region(bucket.getRegion().getRegion());
            policyBucket.region(bucket.getRegion().getRegion());
        }

        try {
            writeMinioClient.makeBucket(
                    makeBucket.build()
            );
            writeMinioClient.setBucketPolicy(
                    policyBucket.build()
            );
        } catch (Exception e) {
            LogHelper.getLogger(MinioServiceImpl.class).error(e.getMessage(), e);
            throw new BusException(e.getMessage());
        }
    }

    private String getPolicy(MaterialPolicy requestPolicy, String bucketName, String customPolicyMsg) {
        if (Objects.equals(requestPolicy, MaterialPolicy.PUBLIC_READ)) {
            return "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::" + bucketName + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\",\"s3:ListMultipartUploadParts\"],\"Resource\":[\"arn:aws:s3:::" + bucketName + "/*\"]}]}";
        }
        if (Objects.equals(requestPolicy, MaterialPolicy.PUBLIC_READ_WRITE)) {
            return "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::" + bucketName + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:GetObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\"],\"Resource\":[\"arn:aws:s3:::" + bucketName + "/*\"]}]}";
        }
        if (Objects.equals(requestPolicy, MaterialPolicy.PRIVATE)) {
            return "{\"Version\":\"2012-10-17\",\"Statement\":[]}";
        }
        if (Objects.equals(requestPolicy, MaterialPolicy.CUSTOM)) {
            return customPolicyMsg;
        }
        return "{\"Version\":\"2012-10-17\",\"Statement\":[]}";
    }

    @Override
    public void removeBucket(BucketName bucketName, Region region) {
        final RemoveBucketArgs.Builder builder = RemoveBucketArgs.builder().bucket(bucketName.getName());
        if (Objects.nonNull(region)) {
            builder.region(region.getRegion());
        }
        try {
            writeMinioClient.removeBucket(
                    builder.build()
            );
        } catch (Exception e) {
            LogHelper.getLogger(MinioServiceImpl.class).error(e.getMessage(), e);
            throw new BusException(e.getMessage());
        }
    }
}
