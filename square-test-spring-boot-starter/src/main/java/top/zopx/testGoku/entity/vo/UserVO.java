package top.zopx.testGoku.entity.vo;

import top.zopx.goku.framework.mybatis.entity.EntityVO;
import top.zopx.goku.framework.web.util.bind.annotation.Desensitization;

import java.time.LocalDateTime;

/**
 * 用户表(User)表实体类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:43
 */
public class UserVO extends EntityVO {
    /**
     * 用户、会员分类
     */
    private String userType;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 真实姓名
     */
    @Desensitization(startIndex = 1, endIndex = 1)
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
     * 手机号
     */
    @Desensitization(startIndex = 3, endIndex = 4)
    private String tel;
    /**
     * 邮箱
     */
    @Desensitization(startIndex = 0, endIndex = 0, charMaskLast = "@")
    private String email;
    /**
     * 账户状态
     */
    private Integer state;
    /**
     * 是否为作者
     */
    private Integer isAuthor;
    /**
     * 推送key
     */
    private String pushKey;
    /**
     * 设备IMEI号
     */
    private String deviceImei;
    /**
     * 是否为vip
     */
    private Integer isVip;
    /**
     * vip结束时间
     */
    private LocalDateTime vipEndTime;
    /**
     * 金币数
     */
    private Double gold;
    /**
     * 版本
     */
    private Integer version;

    /**
     * 注册时间
     */
    private String registryTime;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsAuthor() {
        return isAuthor;
    }

    public void setIsAuthor(Integer isAuthor) {
        this.isAuthor = isAuthor;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getDeviceImei() {
        return deviceImei;
    }

    public void setDeviceImei(String deviceImei) {
        this.deviceImei = deviceImei;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public LocalDateTime getVipEndTime() {
        return vipEndTime;
    }

    public void setVipEndTime(LocalDateTime vipEndTime) {
        this.vipEndTime = vipEndTime;
    }

    public Double getGold() {
        return gold;
    }

    public void setGold(Double gold) {
        this.gold = gold;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

