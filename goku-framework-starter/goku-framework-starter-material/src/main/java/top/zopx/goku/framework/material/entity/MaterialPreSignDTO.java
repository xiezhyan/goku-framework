package top.zopx.goku.framework.material.entity;

import top.zopx.goku.framework.material.constant.MaterialPreCons;
import top.zopx.goku.framework.material.entity.check.BucketName;
import top.zopx.goku.framework.material.entity.check.ObjectName;
import top.zopx.goku.framework.tools.exceptions.BusException;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/31
 */
public class MaterialPreSignDTO {

    /**
     * 名称
     */
    private BucketName bucketName;

    /**
     * 对象名称
     */
    private ObjectName objectName;

    /**
     * 功能
     */
    private MaterialPreCons type;

    /**
     * 设置超时时间
     */
    private Duration expireTime = Duration.ofMinutes(30L);

    /**
     * 额外查询参数
     */
    private Map<String, String> queryParams;

    public MaterialPreSignDTO(BucketName bucketName, MaterialPreCons type, Duration expireTime) {
        this.bucketName = Optional.ofNullable(bucketName).orElseThrow(() -> new BusException("bucket name 不能为空"));
        this.type = type;
        this.expireTime = expireTime;
    }


    public BucketName getBucketName() {
        return bucketName;
    }

    public void setBucketName(BucketName bucketName) {
        this.bucketName = bucketName;
    }

    public MaterialPreCons getType() {
        return type;
    }

    public void setType(MaterialPreCons type) {
        this.type = type;
    }

    public Duration getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Duration expireTime) {
        this.expireTime = expireTime;
    }

    public ObjectName getObjectName() {
        return objectName;
    }

    public void setObjectName(ObjectName objectName) {
        this.objectName = objectName;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    @Override
    public String toString() {
        return "MaterialPreSignDTO{" +
                ", bucketName=" + bucketName +
                ", objectName=" + objectName +
                ", type=" + type +
                ", expireTime=" + expireTime +
                ", queryParams=" + queryParams +
                '}';
    }
}
