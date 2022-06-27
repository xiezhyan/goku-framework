package top.zopx.goku.framework.mybatis.entity;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:08
 */
public class UserLoginVO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 租户
     */
    private Long tenantId;

    public UserLoginVO() {
    }

    public UserLoginVO(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
