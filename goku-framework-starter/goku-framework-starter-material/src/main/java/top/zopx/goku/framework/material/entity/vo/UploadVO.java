package top.zopx.goku.framework.material.entity.vo;

import top.zopx.goku.framework.material.constant.UploadServerEnum;
import top.zopx.goku.framework.material.entity.UploadDTO;
import top.zopx.goku.framework.material.entity.check.BucketName;
import top.zopx.goku.framework.material.entity.check.Region;

import java.io.Serializable;

/**
 * @author 俗世游子
 * @date 2022/05/11
 * @email xiezhyan@126.com
 */
public class UploadVO implements Serializable {

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
     * 类型
     */
    private final BucketName bucketName;
    /**
     * 区域
     */
    private final Region region;

    /**
     * 文件大小
     */
    private final Long size;

    /**
     * 新文件名
     */
    private final String newFileName;

    /**
     * 上传文件完整地址
     */
    private final String overFileUrl;

    /**
     * 文件类型
     */
    private final String contentType;

    /**
     * 上传服务
     */
    private final String endpoint;

    /**
     * 上传服务
     */
    private final UploadServerEnum server;

    private UploadVO(Builder builder) {
        this.userId = builder.userId;
        this.tenantId = builder.tenantId;
        this.originalFilename = builder.originalFilename;
        this.bucketName = builder.bucketName;
        this.region = builder.region;
        this.size = builder.size;
        this.pathObject = builder.pathObject;
        this.newFileName = builder.newFileName;
        this.overFileUrl = builder.overFileUrl;
        this.contentType = builder.contentType;
        this.endpoint = builder.endpoint;
        this.server = builder.server;
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

    public Region getRegion() {
        return region;
    }

    public Long getSize() {
        return size;
    }

    public String getPathObject() {
        return pathObject;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public String getOverFileUrl() {
        return overFileUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public UploadServerEnum getServer() {
        return server;
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
         * 区域
         */
        private Region region;

        /**
         * 文件大小
         */
        private Long size;

        /**
         * 新文件名
         */
        private String newFileName;

        /**
         * 上传文件地址
         */
        private String overFileUrl;

        /**
         * 文件类型
         */
        private String contentType;

        /**
         * 上传服务
         */
        private String endpoint;

        /**
         * 上传服务
         */
        private UploadServerEnum server;

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

        public Builder setRegion(Region region) {
            this.region = region;
            return this;
        }

        public Builder setSize(Long size) {
            this.size = size;
            return this;
        }

        public Builder setPathObject(String pathObject) {
            this.pathObject = pathObject;
            return this;
        }

        public Builder setNewFileName(String newFileName) {
            this.newFileName = newFileName;
            return this;
        }

        public Builder setOverFileUrl(String overFileUrl) {
            this.overFileUrl = overFileUrl;
            return this;
        }

        public Builder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder setEndpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public Builder setServer(UploadServerEnum server) {
            this.server = server;
            return this;
        }

        public Builder setRequest(UploadDTO request) {
            this.contentType = request.getContentType();
            this.bucketName = request.getBucketName();
            this.originalFilename = request.getOriginalFilename();
            this.pathObject = request.getPathObject();
            this.size = request.getSize();
            this.tenantId = request.getTenantId();
            this.userId = request.getUserId();
            return this;
        }
        public UploadVO build() {
            return new UploadVO(this);
        }
    }

    @Override
    public String toString() {
        return "UploadVO{" +
                "userId=" + userId +
                ", tenantId=" + tenantId +
                ", originalFilename='" + originalFilename + '\'' +
                ", pathObject='" + pathObject + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", size=" + size +
                ", newFileName='" + newFileName + '\'' +
                ", materialFileUrl='" + overFileUrl + '\'' +
                ", contentType='" + contentType + '\'' +
                ", endpoint=" + endpoint +
                '}';
    }
}
