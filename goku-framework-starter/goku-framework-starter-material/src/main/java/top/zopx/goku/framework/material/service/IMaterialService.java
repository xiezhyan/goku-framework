package top.zopx.goku.framework.material.service;

import top.zopx.goku.framework.material.entity.MaterialBucket;
import top.zopx.goku.framework.material.entity.check.BucketName;
import top.zopx.goku.framework.material.entity.check.Region;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/5/30
 */
public interface IMaterialService {

    /**
     * bucket是否存在
     *
     * @param bucketName 名称
     * @param region     地区
     * @return 是否
     */
    boolean existsBucket(BucketName bucketName, Region region);

    /**
     * 创建Bucket
     *
     * @param bucket bucket入参
     */
    void createBucket(MaterialBucket bucket);

    /**
     * 移除Bucket
     *
     * @param bucketName 名称
     * @param region     地区
     */
    void removeBucket(BucketName bucketName, Region region);
}
