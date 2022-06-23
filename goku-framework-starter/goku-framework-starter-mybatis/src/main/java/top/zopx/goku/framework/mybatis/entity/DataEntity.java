package top.zopx.goku.framework.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Data Entity 基础类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/3/4
 */
public class DataEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creater;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updater;

    /**
     * 删除时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long deleter;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDelete;

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
