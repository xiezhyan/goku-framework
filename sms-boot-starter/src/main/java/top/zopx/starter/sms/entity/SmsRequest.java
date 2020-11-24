package top.zopx.starter.sms.entity;

import top.zopx.starter.sms.providers.a_li.entity.SmsLiYunRequest;

import java.io.Serializable;

/**
 * @author sanq.Yan
 * @date 2020/11/23
 */
public class SmsRequest implements Serializable {

    private SmsLiYunRequest smsLiYunRequest;

    public SmsLiYunRequest getSmsLiYunRequest() {
        return smsLiYunRequest;
    }

    public void setSmsLiYunRequest(SmsLiYunRequest smsLiYunRequest) {
        this.smsLiYunRequest = smsLiYunRequest;
    }

    public static SmsRequest create() {
        return new SmsRequest();
    }
}
