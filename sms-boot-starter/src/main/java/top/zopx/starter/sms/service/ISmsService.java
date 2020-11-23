package top.zopx.starter.sms.service;

import top.zopx.starter.sms.entity.SmsRequest;
import top.zopx.starter.sms.entity.SmsResponse;

import java.util.List;

/**
 * @author sanq.Yan
 * @date 2020/11/23
 */
public interface ISmsService {

    /**
     * 发送短信
     *
     * @param request 短信对象
     * @return SmsResponse
     */
    SmsResponse sendSms(SmsRequest request) throws Throwable;

    /**
     * 批量发送短信
     * @param requests 短信对象
     * @return SmsResponse
     */
    SmsResponse sendSms(List<SmsRequest> requests) throws Throwable;

}
