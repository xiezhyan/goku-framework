package top.zopx.goku.example.entity.vo;

import top.zopx.goku.framework.mybatis.entity.EntityVO;
import top.zopx.goku.framework.tools.constant.defaults.YesOrNoEnum;
import top.zopx.goku.framework.web.util.bind.annotation.Binding;

/**
 * 角色(Role)表实体类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:44
 */
public class RoleVO extends EntityVO {
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色标识
     */
    private String roleKey;
    /**
     * 状态
     */
    private Integer state;

    /**
     * 显示位置
     */
    private Integer showLoc;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 是否默认
     */
    private Integer isUniversal;
    /**
     * 是否默认
     */
    @Binding(value = "isUniversal", dataSource = YesOrNoEnum.class)
    private String isUniversalRemark;

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

    public String getIsUniversalRemark() {
        return isUniversalRemark;
    }

    public void setIsUniversalRemark(String isUniversalRemark) {
        this.isUniversalRemark = isUniversalRemark;
    }
}

