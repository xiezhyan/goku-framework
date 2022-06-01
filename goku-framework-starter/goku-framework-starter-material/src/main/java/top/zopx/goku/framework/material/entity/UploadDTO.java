package top.zopx.goku.framework.material.entity;

import top.zopx.goku.framework.material.entity.check.BucketName;

import java.util.Arrays;

/**
 * @author 俗世游子
 * @date 2022/05/11
 * @email xiezhyan@126.com
 */
public class UploadDTO {

    /**
     * 归属用户
     */
    private final Long userId;

    /**
     * 租户
     */
    private final Long tenantId;

    /**
     * 原始文件名
     */
    private final String originalFilename;

    /**
     * 默认文件路径
     */
    private final String pathObject;

    /**
     * bucket name
     */
    private final BucketName bucketName;

    /**
     * 文件大小
     */
    private final Long size;

    /**
     * 文件字节数据
     */
    private final byte[] body;

    /**
     * 文件类型
     */
    private final String contentType;

    private UploadDTO(Builder builder) {
        this.userId = builder.userId;
        this.tenantId = builder.tenantId;
        this.originalFilename = builder.originalFilename;
        this.bucketName = builder.bucketName;
        this.size = builder.size;
        this.body = builder.body;
        this.pathObject = builder.pathObject;
        this.contentType = builder.contentType;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public BucketName getBucketName() {
        return bucketName;
    }

    public Long getSize() {
        return size;
    }

    public byte[] getBody() {
        return body;
    }

    public String getPathObject() {
        return pathObject;
    }

    public String getContentType() {
        return contentType;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        /**
         * 归属用户
         */
        private Long userId;

        /**
         * 租户
         */
        private Long tenantId;

        /**
         * 原始文件名
         */
        private String originalFilename;

        /**
         * 默认文件路径
         */
        private String pathObject;

        /**
         * 类型
         */
        private BucketName bucketName;

        /**
         * 文件大小
         */
        private Long size;

        /**
         * 文件字节数据
         */
        private byte[] body;

        /**
         * 文件类型
         */
        private String contentType;

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setTenantId(Long tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder setOriginalFilename(String originalFilename) {
            this.originalFilename = originalFilename;
            return this;
        }

        public Builder setBucketName(BucketName bucketName) {
            this.bucketName = bucketName;
            return this;
        }

        public Builder setSize(Long size) {
            this.size = size;
            return this;
        }

        public Builder setBody(byte[] body) {
            this.body = body;
            return this;
        }

        public Builder setPathObject(String pathObject) {
            this.pathObject = pathObject;
            return this;
        }

        public Builder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public UploadDTO build() {
            return new UploadDTO(this);
        }
    }

    @Override
    public String toString() {
        return "UploadDTO{" +
                "userId=" + userId +
                ", tenantId=" + tenantId +
                ", originalFilename='" + originalFilename + '\'' +
                ", pathObject='" + pathObject + '\'' +
                ", bucketName=" + bucketName +
                ", size=" + size +
                ", body=" + Arrays.toString(body) +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
