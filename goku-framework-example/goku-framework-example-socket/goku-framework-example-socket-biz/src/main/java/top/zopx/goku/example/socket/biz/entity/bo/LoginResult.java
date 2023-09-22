package top.zopx.goku.example.socket.biz.entity.bo;

import java.io.Serializable;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/11/14 20:12
 */
public class LoginResult implements Serializable {

    private Long userId;

    private String userName;

    private String ticket;

    private String ukey;

    private Long ukeyExpireAt;

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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }

    public Long getUkeyExpireAt() {
        return ukeyExpireAt;
    }

    public void setUkeyExpireAt(Long ukeyExpireAt) {
        this.ukeyExpireAt = ukeyExpireAt;
    }
}
