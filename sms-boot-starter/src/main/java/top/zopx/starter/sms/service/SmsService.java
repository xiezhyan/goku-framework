package top.zopx.starter.sms.service;

import top.zopx.starter.sms.entity.SmsRequest;
import top.zopx.starter.sms.entity.SmsResponse;

/**
 * top.zopx.starter.sms.service.SmsService
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */
public interface SmsService {

    /**
     * 单条短信发送
     */
    SmsResponse sendSms(SmsRequest smsRequest);

}
