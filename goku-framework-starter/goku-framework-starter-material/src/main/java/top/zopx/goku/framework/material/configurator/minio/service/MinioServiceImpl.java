package top.zopx.goku.framework.material.configurator.minio.service;

import io.minio.*;
import io.minio.http.Method;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import top.zopx.goku.framework.material.configurator.minio.client.MinIOClientConfigurator;
import top.zopx.goku.framework.material.configurator.minio.properties.BootstrapMinIO;
import top.zopx.goku.framework.material.constant.MaterialPolicy;
import top.zopx.goku.framework.material.constant.MaterialPreCons;
import top.zopx.goku.framework.material.constant.UploadServerEnum;
import top.zopx.goku.framework.material.entity.MaterialBucketDTO;
import top.zopx.goku.framework.material.entity.MaterialPreSignDTO;
import top.zopx.goku.framework.material.entity.UploadDTO;
import top.zopx.goku.framework.material.entity.check.BucketName;
import top.zopx.goku.framework.material.entity.check.ObjectName;
import top.zopx.goku.framework.material.entity.vo.MaterialPreSignVO;
import top.zopx.goku.framework.material.entity.vo.UploadVO;
import top.zopx.goku.framework.material.service.IMaterialService;
import top.zopx.goku.framework.material.util.ObjectNameUtil;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @Resource
    private BootstrapMinIO bootstrapMinIO;

    @Override
    public boolean existsBucket(BucketName bucketName) {
        try {
            bucketName = Optional.ofNullable(bucketName).orElseThrow(() -> new BusException("bucket 不能为空"));

            BucketExistsArgs.Builder bucket = BucketExistsArgs.builder()
                    .bucket(bucketName.getName());

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
        bucket = Optional.ofNullable(bucket).orElseThrow(() -> new BusException("创建Bucket参数为空"));

        if (existsBucket(bucket.getBucketName())) {
            throw new BusException("当前Bucket已存在");
        }

        try {
            writeMinioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucket.getBucketName().getName()).build()
            );
            writeMinioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucket.getBucketName().getName())
                            .config(getPolicy(bucket.getPolicy(), bucket.getBucketName().getName(), bucket.getCustomPolicyMsg()))
                            .build()
            );
        } catch (Exception e) {
            LogHelper.getLogger(MinioServiceImpl.class).error(e.getMessage(), e);
            throw new BusException(e.getMessage());
        }
    }

    @Override
    public void setPolicy(MaterialBucketDTO bucket) {
        bucket = Optional.ofNullable(bucket).orElseThrow(() -> new BusException("创建Bucket参数为空"));
        try {
            writeMinioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucket.getBucketName().getName())
                            .config(getPolicy(bucket.getPolicy(), bucket.getBucketName().getName(), bucket.getCustomPolicyMsg()))
                            .build()
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
    public void removeBucket(BucketName bucketName) {
        bucketName = Optional.ofNullable(bucketName).orElseThrow(() -> new BusException("bucket 不能为空"));

        try {
            writeMinioClient.removeBucket(
                    RemoveBucketArgs.builder().bucket(bucketName.getName()).build()
            );
        } catch (Exception e) {
            LogHelper.getLogger(MinioServiceImpl.class).error(e.getMessage(), e);
            throw new BusException(e.getMessage());
        }
    }

    @Override
    public MaterialPreSignVO genPreSignUrl(MaterialPreSignDTO materialPreSignDTO) {
        materialPreSignDTO = Optional.ofNullable(materialPreSignDTO).orElseThrow(() -> new BusException("生成防伪链接参数为空"));

        GetPresignedObjectUrlArgs.Builder builder =
                GetPresignedObjectUrlArgs.builder().bucket(materialPreSignDTO.getBucketName().getName());

        if (MapUtils.isNotEmpty(materialPreSignDTO.getQueryParams())) {
            builder = builder.extraQueryParams(materialPreSignDTO.getQueryParams());
        }

        try {
            final String url = writeMinioClient.getPresignedObjectUrl(
                    builder
                            .object(materialPreSignDTO.getObjectName().getName())
                            .method(getMethod(materialPreSignDTO.getType())).build()
            );
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
                String newFileName = ObjectNameUtil.getNewFileName(uploadDTO.getOriginalFilename(), uploadDTO.getContentType());
                final String objectName = MessageFormat.format("{0}/{1}", uploadDTO.getPathObject(), newFileName);

                GetPresignedObjectUrlArgs.Builder getPresignedBuild = GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(uploadDTO.getBucketName().getName())
                        .object(objectName);

                writeMinioClient.putObject(
                        PutObjectArgs.builder()
                                .contentType(uploadDTO.getContentType())
                                .bucket(uploadDTO.getBucketName().getName())
                                .object(objectName)
                                .stream(new ByteArrayInputStream(uploadDTO.getBody()), uploadDTO.getSize(), 0).build()
                );

                resultList.add(
                        UploadVO.create()
                                .setRequest(uploadDTO)
                                .setEndpoint(bootstrapMinIO.getEndpoint())
                                .setNewFileName(newFileName)
                                .setServer(UploadServerEnum.MINIO)
                                .setOverFileUrl(writeMinioClient.getPresignedObjectUrl(getPresignedBuild.build()))
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
    public void remove(BucketName bucketName, ObjectName objectName) {
        bucketName = Optional.ofNullable(bucketName).orElseThrow(() -> new BusException("bucket 不能为空"));
        objectName = Optional.ofNullable(objectName).orElseThrow(() -> new BusException("对象名称不能为空"));

        try {
            writeMinioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName.getName())
                            .object(objectName.getName()).build()
            );
        } catch (Exception e) {
            LogHelper.getLogger(MinioServiceImpl.class).error(e.getMessage(), e);
            throw new BusException(e.getMessage());
        }
    }

    private Method getMethod(MaterialPreCons type) {
        if (Objects.equals(type, MaterialPreCons.GET)) {
            return Method.GET;
        }
        if (Objects.equals(type, MaterialPreCons.DIRECT_UPLOAD)) {
            return Method.PUT;
        }
        return Method.PUT;
    }
}
