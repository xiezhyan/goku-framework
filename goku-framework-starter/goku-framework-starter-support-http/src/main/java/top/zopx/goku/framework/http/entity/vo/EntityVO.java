package top.zopx.goku.framework.http.entity.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/06/25 9:47
 */
public class EntityVO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private Long creater;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    private Long updater;

    /**
     * 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 创建时间
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建人
     */
    public Long getCreater() {
        return creater;
    }

    /**
     * 创建人
     */
    public void setCreater(Long creater) {
        this.creater = creater;
    }

    /**
     * 修改时间
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 修改人
     */
    public Long getUpdater() {
        return updater;
    }

    /**
     * 修改人
     */
    public void setUpdater(Long updater) {
        this.updater = updater;
    }

}
