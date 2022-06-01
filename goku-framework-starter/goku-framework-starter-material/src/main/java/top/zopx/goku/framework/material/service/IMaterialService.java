package top.zopx.goku.framework.material.service;

import top.zopx.goku.framework.material.entity.MaterialBucketDTO;
import top.zopx.goku.framework.material.entity.MaterialPreSignDTO;
import top.zopx.goku.framework.material.entity.UploadDTO;
import top.zopx.goku.framework.material.entity.check.BucketName;
import top.zopx.goku.framework.material.entity.check.ObjectName;
import top.zopx.goku.framework.material.entity.check.Region;
import top.zopx.goku.framework.material.entity.vo.MaterialPreSignVO;
import top.zopx.goku.framework.material.entity.vo.UploadVO;

import java.util.List;

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
    void createBucket(MaterialBucketDTO bucket);

    /**
     * 移除Bucket
     *
     * @param bucketName 名称
     * @param region     地区
     */
    void removeBucket(BucketName bucketName, Region region);

    /**
     * 生成防伪链接
     *
     * @param materialPreSignDTO materialPreSignDTO
     * @return MaterialPreSignVO
     */
    MaterialPreSignVO genPreSignUrl(MaterialPreSignDTO materialPreSignDTO);

    /**
     * 基础文件上传
     * 仅支持小文件上传，如需上传大文件，请使用直传方式
     *
     * @param uploads 上传参数
     * @return List<UploadVO>
     */
    List<UploadVO> upload(List<UploadDTO> uploads);

    /**
     * 删除文件
     *
     * @param bucketName bucket
     * @param region     区域
     * @param objectName 对象名称
     */
    void remove(BucketName bucketName, Region region, ObjectName objectName);
}
