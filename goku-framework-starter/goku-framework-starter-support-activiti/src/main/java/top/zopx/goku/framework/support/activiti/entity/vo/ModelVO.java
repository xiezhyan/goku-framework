package top.zopx.goku.framework.support.activiti.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程列表
 *
 * @author Mr.Xie
 * @date 2021/6/24
 * @email xiezhyan@126.com
 */
public class ModelVO implements Serializable {

    /**
     * 编号
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 唯一标识
     */
    private String key;
    /**
     * 分类
     */
    private String category;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后一次修改时间
     */
    private Date lastUpdateTime;
    /**
     * 元数据信息
     */
    private String metaInfo;
    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 租户
     */
    private String tenantId;

    public ModelVO() {
    }

    public ModelVO(String id, String name, String key, String category, Date createTime, Date lastUpdateTime, String metaInfo, String deploymentId, String tenantId) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.category = category;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.metaInfo = metaInfo;
        this.deploymentId = deploymentId;
        this.tenantId = tenantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
