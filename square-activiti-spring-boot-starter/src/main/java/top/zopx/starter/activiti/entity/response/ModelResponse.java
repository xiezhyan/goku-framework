package top.zopx.starter.activiti.entity.response;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程列表
 *
 * @author 俗世游子
 * @date 2021/6/24
 * @email xiezhyan@126.com
 */
public class ModelResponse implements Serializable {

    private String id;
    private String name;
    private String key;
    private String category;
    private Date createTime;
    private Date lastUpdateTime;
    private String metaInfo;
    private String deploymentId;

    private Integer version;

    public ModelResponse() {
    }

    public ModelResponse(String id, String name, String key, String category, Date createTime, Date lastUpdateTime, String metaInfo, String deploymentId) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.category = category;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.metaInfo = metaInfo;
        this.deploymentId = deploymentId;
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
}
