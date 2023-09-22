package top.zopx.goku.framework.oss.constant;

import top.zopx.goku.framework.tools.exception.IBus;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/22
 */
public enum OssErrorEnum implements IBus {

    /**
     * 创建bucket异常
     */
    CREATE_BUCKET_ERROR(5000, "oss.bucket.create"),
    /**
     * 删除bucket异常
     */
    DELETE_BUCKET_ERROR(5000, "oss.bucket.delete"),
    /**
     * bucket权限异常
     */
    ACL_BUCKET_ERROR(5000, "oss.bucket.acl"),

    /**
     * 查询指定object信息
     */
    GET_OBJECT_ERROR(5010, "oss.object.get"),
    /**
     *查询指定object元数据信息
     */
    GET_OBJECT_METADATA_ERROR(5010, "oss.object.meta.get"),
    /**
     *删除object异常
     */
    DELETE_OBJECT_ERROR(5010, "oss.object.delete"),
    /**
     *上传异常
     */
    PUT_OBJECT_ERROR(5010, "oss.object.put"),
    /**
     *copy异常
     */
    COPY_OBJECT_ERROR(5010, "oss.object.copy"),
    /**
     *acl异常
     */
    ACL_OBJECT_ERROR(5010, "oss.object.acl"),

    /**
     *初始化分片上传异常
     */
    INITIAL_PART_ERROR(5020, "oss.part.initial"),
    /**
     *上传分片异常
     */
    UPLOAD_PART_ERROR(5020, "oss.part.upload"),
    /**
     *分片上传完成异常
     */
    COMPLETE_PART_ERROR(5020, "oss.part.complete"),
    /**
     *终止分片上传异常
     */
    ABORT_PART_ERROR(5020, "oss.part.abort"),

    /**
     *列举分片异常
     */
    LIST_PART_ERROR(5020, "oss.part.list"),
    ;
    private final int code;
    private final String msg;

    OssErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
