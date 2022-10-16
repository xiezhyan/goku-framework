package top.zopx.testGoku.entity.dto;

import top.zopx.goku.framework.mybatis.entity.BaseEntity;
import top.zopx.goku.framework.web.util.validate.annotations.MutiPattern;

import java.util.List;

/**
 * 用户表(User)表实体类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:43
 */
public class UserDTO extends BaseEntity {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 头像
     */
    private String avator;

    /**
     * 登录账号：
     *  - 手机号、邮箱任选其一
     */
    @MutiPattern(
            patterns = {"0?(13|14|15|17|18)[0-9]{9}", "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?"},
            logic = MutiPattern.Logic.OR,
            message = "登录账号格式不正确：请输入正确的手机号或邮箱"
    )
    private String account;

    /**
     * 角色ID
     */
    private List<Long> roleId;

    /**
     * 用户状态
     */
    private Integer state;

    /**
     * 是否开通VIP
     */
    private Integer isVip;

    /**
     * 是否为作者
     */
    private Integer isAuthor;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<Long> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<Long> roleId) {
        this.roleId = roleId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public Integer getIsAuthor() {
        return isAuthor;
    }

    public void setIsAuthor(Integer isAuthor) {
        this.isAuthor = isAuthor;
    }
}

