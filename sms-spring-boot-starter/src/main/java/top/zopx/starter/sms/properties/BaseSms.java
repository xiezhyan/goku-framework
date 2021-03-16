package top.zopx.starter.sms.properties;

/**
 * @author sanq.Yan
 * @date 2020/11/23
 */
public class BaseSms {

    /**
     * 是否开启
     */
    private Boolean open = false;
    /**
     * 签名,批量发送无效
     */
    private String signName;

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}
