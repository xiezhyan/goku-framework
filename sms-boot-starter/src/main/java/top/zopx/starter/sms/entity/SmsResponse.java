package top.zopx.starter.sms.entity;

import java.io.Serializable;

/**
 * @author sanq.Yan
 * @date 2020/11/23
 */
public class SmsResponse implements Serializable {

    private String desc;

    private Integer status;

    public SmsResponse() {
    }

    public SmsResponse(String desc, Integer status) {
        this.desc = desc;
        this.status = status;
    }

    public static SmsResponse error() {
        return new SmsResponse("参数为空", 500);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
