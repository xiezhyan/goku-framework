package top.zopx.goku.framework.material.configurator.minio.service;

import io.minio.*;
import io.minio.http.Method;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import top.zopx.goku.framework.material.configurator.minio.client.MinIOClientConfigurator;
import top.zopx.goku.framework.material.constant.MaterialPolicy;
import top.zopx.goku.framework.material.constant.MaterialPreCons;
import top.zopx.goku.framework.material.entity.MaterialBucketDTO;
import top.zopx.goku.framework.material.entity.MaterialPreSignDTO;
import top.zopx.goku.framework.material.entity.UploadDTO;
import top.zopx.goku.framework.material.entity.check.BucketName;
import top.zopx.goku.framework.material.entity.check.ObjectName;
import top.zopx.goku.framework.material.entity.check.Region;
import top.zopx.goku.framework.material.entity.vo.MaterialPreSignVO;
import top.zopx.goku.framework.material.entity.vo.UploadVO;
import top.zopx.goku.framework.material.service.IMaterialService;
import top.zopx.goku.framework.material.util.ObjectNameUtil;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.util.string.StringUtil;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
            BucketExistsArgs.Builder bucket = BucketExistsArgs.builder()
                    .bucket(bucketName.getName());

            if (Objects.nonNull(region)) {
                bucket = bucket.region(region.getRegion());
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
    public void createBucket(MaterialBucketDTO bucket) {
        if (!existsBucket(bucket.getBucketName(), bucket.getRegion())) {
            throw new BusException("当前Bucket已存在");
        }

        MakeBucketArgs.Builder makeBucket = MakeBucketArgs.builder().bucket(bucket.getBucketName().getName());
        SetBucketPolicyArgs.Builder policyBucket = SetBucketPolicyArgs.builder()
                .bucket(bucket.getBucketName().getName())
                .config(getPolicy(bucket.getPolicy(), bucket.getBucketName().getName(), bucket.getCustomPolicyMsg()));

        if (Objects.nonNull(bucket.getRegion())) {
            makeBucket = makeBucket.region(bucket.getRegion().getRegion());
            policyBucket = policyBucket.region(bucket.getRegion().getRegion());
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
        RemoveBucketArgs.Builder builder = RemoveBucketArgs.builder().bucket(bucketName.getName());
        if (Objects.nonNull(region)) {
            builder = builder.region(region.getRegion());
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

    @Override
    public MaterialPreSignVO genPreSignUrl(MaterialPreSignDTO materialPreSignDTO) {
        GetPresignedObjectUrlArgs.Builder builder =
                GetPresignedObjectUrlArgs.builder().bucket(materialPreSignDTO.getBucketName().getName());

        if (Objects.nonNull(materialPreSignDTO.getRegion())) {
            builder = builder.region(materialPreSignDTO.getRegion().getRegion());
        }

        try {
            builder = builder
                    .object(materialPreSignDTO.getObjectName().getName())
                    .method(getMethod(materialPreSignDTO.getType()))
                    .extraQueryParams(materialPreSignDTO.getQueryParams())
                    .expiry(StringUtil.toInteger(materialPreSignDTO.getExpireTime().toMinutes()), TimeUnit.MINUTES);
            final String url = writeMinioClient.getPresignedObjectUrl(builder.build());
            final MaterialPreSignVO result = new MaterialPreSignVO();
            result.setHost(url);
            return result;
        } catch (Exception e) {
            LogHelper.getLogger(MinioServiceImpl.class).error(e.getMessage(), e);
            throw new BusException(e.getMessage());
        }
    }

    @Override
    public List<UploadVO> upload(List<UploadDTO> uploads) {
        if (CollectionUtils.isEmpty(uploads)) {
            throw new BusException("文件参数为空");
        }
        List<UploadVO> resultList = new ArrayList<>(uploads.size());
        uploads.forEach(uploadDTO -> {
            try {
                String newFileName = ObjectNameUtil.getNewFileName(uploadDTO.getOriginalFilename());
                final String objectName = MessageFormat.format("{0}/{1}", uploadDTO.getPathObject(), newFileName);

                PutObjectArgs.Builder putObjectBuild = PutObjectArgs.builder()
                        .contentType(uploadDTO.getContentType())
                        .bucket(uploadDTO.getBucketName().getName())
                        .object(objectName)
                        .stream(new ByteArrayInputStream(uploadDTO.getBody()), uploadDTO.getSize(), 0);

                GetPresignedObjectUrlArgs.Builder getPresignedBuild = GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .expiry(7)
                        .bucket(uploadDTO.getBucketName().getName())
                        .object(objectName);

                if (Objects.nonNull(uploadDTO.getRegion())) {
                    putObjectBuild = putObjectBuild.region(uploadDTO.getRegion().getRegion());
                    getPresignedBuild = getPresignedBuild.region(uploadDTO.getRegion().getRegion());
                }

                writeMinioClient.putObject(putObjectBuild.build());

                resultList.add(
                        UploadVO.create()
                                .setRequest(uploadDTO)
                                .setUploadServerId(1)
                                .setNewFileName(newFileName)
                                .setMaterialFileUrl(writeMinioClient.getPresignedObjectUrl(getPresignedBuild.build()))
                                .build()
                );
            } catch (Exception e) {
                LogHelper.getLogger(MinioServiceImpl.class).error(e.getMessage(), e);
                throw new BusException(e.getMessage());
            }
        });
        return resultList;
    }


    @Override
    public void remove(BucketName bucketName, Region region, ObjectName objectName) {
        bucketName = Optional.ofNullable(bucketName).orElseThrow(() -> new BusException("bucket 不能为空"));
        objectName = Optional.ofNullable(objectName).orElseThrow(() -> new BusException("对象名称不能为空"));

        RemoveObjectArgs.Builder builder = RemoveObjectArgs.builder()
                .bucket(bucketName.getName())
                .object(objectName.getName());

        if (Objects.nonNull(region)) {
            builder =  builder.region(region.getRegion());
        }

        try {
            writeMinioClient.removeObject(builder.build());
        } catch (Exception e) {
            LogHelper.getLogger(MinioServiceImpl.class).error(e.getMessage(), e);
            throw new BusException(e.getMessage());
        }
    }

    private Method getMethod(MaterialPreCons type) {
        if (Objects.equals(type, MaterialPreCons.GET)) {
            return Method.GET;
        }
        if (Objects.equals(type, MaterialPreCons.POST)) {
            return Method.PUT;
        }
        return Method.PUT;
    }
}
