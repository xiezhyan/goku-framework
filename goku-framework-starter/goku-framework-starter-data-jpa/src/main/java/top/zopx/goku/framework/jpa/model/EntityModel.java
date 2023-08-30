package top.zopx.goku.framework.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Data Entity 基础类
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/3/4
 */
@MappedSuperclass
public abstract class EntityModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "snowflakeId", strategy = "top.zopx.goku.framework.jpa.strategy.SnowflakeStrategy")
    @GeneratedValue(generator = "snowflakeId")
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Column(name = "creater", updatable = false)
    private Long creater;

    /**
     * 修改时间
     */
    @Column(name = "update_time", insertable = false)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Column(name = "updater", insertable = false)
    private Long updater;

    /**
     * 删除时间
     */
    @Column(name = "delete_time", insertable = false)
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @Column(name = "deleter", insertable = false)
    private Long deleter;

    /**
     * 是否删除
     */
    @Column(name = "is_delete", columnDefinition = "tinyint comment '是否删除 0.否 1.是' default 0")
    private Integer isDelete;

    public Long getId() {
        return id;
    }

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

    /**
     * 删除时间
     */
    public LocalDateTime getDeleteTime() {
        return deleteTime;
    }

    /**
     * 删除时间
     */
    public void setDeleteTime(LocalDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * 删除人
     */
    public Long getDeleter() {
        return deleter;
    }

    /**
     * 删除人
     */
    public void setDeleter(Long deleter) {
        this.deleter = deleter;
    }

    /**
     * 是否删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 是否删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

}
