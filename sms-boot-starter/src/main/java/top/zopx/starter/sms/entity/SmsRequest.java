package top.zopx.starter.sms.entity;

import java.io.Serializable;

/**
 * @author sanq.Yan
 * @date 2020/11/23
 */
public class SmsRequest implements Serializable {

    private SmsALiYunRequest smsALiYunRequest;

    public SmsALiYunRequest getSmsALiYunRequest() {
        return smsALiYunRequest;
    }

    public void setSmsALiYunRequest(SmsALiYunRequest smsALiYunRequest) {
        this.smsALiYunRequest = smsALiYunRequest;
    }
}
