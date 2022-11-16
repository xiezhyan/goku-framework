package top.zopx.goku.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import top.zopx.goku.framework.mybatis.entity.DataEntity;

/**
 * 角色(Role)表实体类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:44
 */
@TableName("tbl_role")
public class Role extends DataEntity {
    /**
     * 角色名称
     */
    @TableField("`role_name`")
    private String roleName;

    /**
     * 角色标识
     */
    @TableField("`role_key`")
    private String roleKey;
    /**
     * 状态
     */
    @TableField("`state`")
    private Integer state;
    /**
     * 显示位置
     */
    @TableField("`show_loc`")
    private Integer showLoc;
    /**
     * 版本
     */
    @TableField("`version`")
    private Integer version;

    @TableField("`is_universal`")
    private Integer isUniversal;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getShowLoc() {
        return showLoc;
    }

    public void setShowLoc(Integer showLoc) {
        this.showLoc = showLoc;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public Integer getIsUniversal() {
        return isUniversal;
    }

    public void setIsUniversal(Integer isUniversal) {
        this.isUniversal = isUniversal;
    }
}
