package top.zopx.goku.framework.material.entity;

import top.zopx.goku.framework.material.constant.MaterialPolicy;
import top.zopx.goku.framework.material.entity.check.BucketName;
import top.zopx.goku.framework.tools.exceptions.BusException;

import java.util.Optional;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/30
 */
public class MaterialBucketDTO {

    /**
     * 名称
     */
    private BucketName bucketName;

    /**
     * 权限
     */
    private MaterialPolicy policy;

    public MaterialBucketDTO(BucketName bucketName, MaterialPolicy policy) {
        this.bucketName = Optional.ofNullable(bucketName).orElseThrow(() -> new BusException("bucket name 不能为空"));
        this.policy = policy;
    }

    /**
     * 自定义内容
     */
    private String customPolicyMsg;

    public BucketName getBucketName() {
        return bucketName;
    }

    public void setBucketName(BucketName bucketName) {
        this.bucketName = bucketName;
    }

    public MaterialPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(MaterialPolicy policy) {
        this.policy = policy;
    }

    public String getCustomPolicyMsg() {
        return customPolicyMsg;
    }

    public void setCustomPolicyMsg(String customPolicyMsg) {
        this.customPolicyMsg = customPolicyMsg;
    }

    @Override
    public String toString() {
        return "MaterialBucketDTO{" +
                ", bucketName=" + bucketName +
                ", policy=" + policy +
                ", customPolicyMsg='" + customPolicyMsg + '\'' +
                '}';
    }
}
