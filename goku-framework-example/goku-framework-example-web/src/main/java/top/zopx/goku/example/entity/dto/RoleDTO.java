package top.zopx.goku.example.entity.dto;

import org.hibernate.validator.constraints.Range;
import top.zopx.goku.framework.mybatis.entity.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 角色(Role)表实体类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:44
 */
public class RoleDTO extends BaseEntity {
    /**
     * 角色名称
     */
    @NotBlank(message = "NotBlank.role.roleName")
    private String roleName;
    /**
     * 角色标识
     */
    @NotBlank(message = "NotBlank.role.roleKey")
    private String roleKey;
    /**
     * 状态
     */
    @NotNull(message = "NotNull.role.state")
    @Range(min = 0, max = 1, message = "Range.role.state")
    private Integer state;
    /**
     * 显示位置
     */
    private Integer showLoc;
    /**
     * 版本
     */
    private Integer version;

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

