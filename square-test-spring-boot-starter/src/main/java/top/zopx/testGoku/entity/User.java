package top.zopx.testGoku.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.RandomStringUtils;
import top.zopx.goku.framework.mybatis.entity.DataEntity;

import java.time.LocalDateTime;

/**
 * 用户表(User)表实体类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-07-23 12:33:43
 */
@TableName("tbl_user")
public class User extends DataEntity {
    /**
     * 应用ID
     */
    @TableField("`app_id`")
    private Long appId;
    /**
     * 用户、会员分类
     */
    @TableField("`user_type`")
    private String userType;
    /**
     * 昵称
     */
    @TableField("`nick_name`")
    private String nickName;
    /**
     * 真实姓名
     */
    @TableField("`real_name`")
    private String realName;
    /**
     * 性别
     */
    @TableField("`gender`")
    private String gender;
    /**
     * 头像
     */
    @TableField("`avator`")
    private String avator;
    /**
     * 手机号
     */
    @TableField("`tel`")
    private String tel;
    /**
     * 邮箱
     */
    @TableField("`email`")
    private String email;
    /**
     * 冗余密码
     */
    @TableField("`password`")
    private String password;
    /**
     * 账户状态
     */
    @TableField("`state`")
    private Integer state;
    /**
     * 是否为作者
     */
    @TableField("`is_author`")
    private Integer isAuthor;
    /**
     * 推送key
     */
    @TableField("`push_key`")
    private String pushKey;
    /**
     * 设备IMEI号
     */
    @TableField("`device_imei`")
    private String deviceImei;
    /**
     * 是否为vip
     */
    @TableField("`is_vip`")
    private Integer isVip;
    /**
     * vip结束时间
     */
    @TableField("`vip_end_time`")
    private LocalDateTime vipEndTime;
    /**
     * 金币数
     */
    @TableField("`gold`")
    private Double gold;
    /**
     * 版本
     */
    @TableField("`version`")
    private Integer version;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    private static final int NICK_NAME_LENGTH = 8;

    public String genericNickName() {
        return RandomStringUtils.randomAlphabetic(NICK_NAME_LENGTH);
    }
}
