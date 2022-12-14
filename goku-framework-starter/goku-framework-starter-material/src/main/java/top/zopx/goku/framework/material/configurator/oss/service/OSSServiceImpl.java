package top.zopx.goku.framework.material.configurator.oss.service;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import io.minio.http.Method;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import top.zopx.goku.framework.material.configurator.oss.client.OSSClientConfigurator;
import top.zopx.goku.framework.material.configurator.oss.properties.BootstrapOSS;
import top.zopx.goku.framework.material.constant.MaterialPolicy;
import top.zopx.goku.framework.material.constant.MaterialPreCons;
import top.zopx.goku.framework.material.constant.UploadServerEnum;
import top.zopx.goku.framework.material.entity.MaterialBucketDTO;
import top.zopx.goku.framework.material.entity.MaterialPreDTO;
import top.zopx.goku.framework.material.entity.UploadDTO;
import top.zopx.goku.framework.material.entity.check.BucketName;
import top.zopx.goku.framework.material.entity.check.ObjectName;
import top.zopx.goku.framework.material.entity.vo.MaterialPreVO;
import top.zopx.goku.framework.material.entity.vo.UploadVO;
import top.zopx.goku.framework.material.service.IMaterialService;
import top.zopx.goku.framework.material.util.ObjectNameUtil;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:31
 */
@Service(IMaterialService.OSS_SERVER)
@ConditionalOnBean(OSSClientConfigurator.OssMarker.class)
public class OSSServiceImpl implements IMaterialService {

    @Resource
    @SuppressWarnings("all")
    private OSS writeOSSClient;
    @Resource
    private BootstrapOSS bootstrapOSS;

    @Override
    public boolean existsBucket(BucketName bucketName) {
        bucketName = Optional.ofNullable(bucketName).orElseThrow(() -> new BusException("bucket不能为空"));
        return writeOSSClient.doesBucketExist(bucketName.getName());
    }

    @Override
    public void createBucket(MaterialBucketDTO bucket) {
        bucket = Optional.ofNullable(bucket).orElseThrow(() -> new BusException("创建Bucket参数为空"));

        if (existsBucket(bucket.getBucketName())) {
            throw new BusException("当前Bucket已存在");
        }

        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket.getBucketName().getName());
        createBucketRequest.setCannedACL(getCannedAccessControlListByPolicy(bucket.getPolicy()));
        writeOSSClient.createBucket(createBucketRequest);
    }

    @Override
    public void setPolicy(MaterialBucketDTO bucket) {
        bucket = Optional.ofNullable(bucket).orElseThrow(() -> new BusException("权限Bucket参数为空"));
        writeOSSClient.setBucketAcl(bucket.getBucketName().getName(), getCannedAccessControlListByPolicy(bucket.getPolicy()));
    }

    @Override
    public void removeBucket(BucketName bucketName) {
        bucketName = Optional.ofNullable(bucketName).orElseThrow(() -> new BusException("bucket 不能为空"));
        writeOSSClient.deleteBucket(bucketName.getName());
    }

    @Override
    public MaterialPreVO uploadPre(MaterialPreDTO materialPreDTO) {
        materialPreDTO = Optional.ofNullable(materialPreDTO).orElseThrow(() -> new BusException("生成防伪链接参数为空"));
        final MaterialPreVO result = new MaterialPreVO();

        result.setHost(
                generatePresignedUrl(
                        materialPreDTO.getBucketName(),
                        materialPreDTO.getObjectName(),
                        materialPreDTO.getExpireTime(),
                        getMethod(materialPreDTO.getType())
                )
        );
        return result;
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

                PutObjectRequest request = new PutObjectRequest(uploadDTO.getBucketName().getName(), objectName, new ByteArrayInputStream(uploadDTO.getBody()));
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(uploadDTO.getContentType());
                request.setMetadata(metadata);
                writeOSSClient.putObject(request);

                resultList.add(
                        UploadVO.create()
                                .setRequest(uploadDTO)
                                .setEndpoint(
                                        MessageFormat.format("{0}://{1}.{2}",
                                                Boolean.TRUE.equals(bootstrapOSS.getSupportHttps()) ? "https" : "http",
                                                uploadDTO.getBucketName().getName(),
                                                bootstrapOSS.getEndpoint()
                                        )
                                )
                                .setServer(UploadServerEnum.OSS)
                                .setNewFileName(newFileName)
                                .setOverFileUrl(generatePresignedUrl(uploadDTO.getBucketName(), new ObjectName(objectName), Duration.ofDays(10L * 365), HttpMethod.GET))
                                .build()
                );
            } catch (Exception e) {
                LogHelper.getLogger(OSSServiceImpl.class).error(e.getMessage(), e);
                throw new BusException(e.getMessage());
            }
        });

        return resultList;
    }

    @Override
    public void remove(BucketName bucketName, ObjectName objectName) {
        bucketName = Optional.ofNullable(bucketName).orElseThrow(() -> new BusException("bucket 不能为空"));
        objectName = Optional.ofNullable(objectName).orElseThrow(() -> new BusException("对象名称不能为空"));

        writeOSSClient.deleteObject(bucketName.getName(), objectName.getName());
    }

    /**
     * 生成查看地址
     *
     * @param bucketName bucket
     * @param objectName object
     * @param expireTime 过期时间 单位
     * @return 地址
     */
    private String generatePresignedUrl(BucketName bucketName, ObjectName objectName, Duration expireTime, HttpMethod method) {
        Date expiration = new Date(System.currentTimeMillis() + expireTime.toMillis());
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = writeOSSClient.generatePresignedUrl(bucketName.getName(), objectName.getName(), expiration, method);
        return url.toString();
    }

    private CannedAccessControlList getCannedAccessControlListByPolicy(MaterialPolicy policy) {
        if (Objects.equals(policy, MaterialPolicy.PUBLIC_READ)) {
            return CannedAccessControlList.PublicRead;
        }
        if (Objects.equals(policy, MaterialPolicy.PUBLIC_READ_WRITE)) {
            return CannedAccessControlList.PublicReadWrite;
        }
        if (Objects.equals(policy, MaterialPolicy.PRIVATE)) {
            return CannedAccessControlList.Private;
        }
        return CannedAccessControlList.Default;
    }

    private HttpMethod getMethod(MaterialPreCons type) {
        if (Objects.equals(type, MaterialPreCons.GET)) {
            return HttpMethod.GET;
        }
        return HttpMethod.PUT;
    }
}
