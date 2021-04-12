package top.zopx.starter.sms.service;

import top.zopx.starter.sms.entity.SmsRequest;
import top.zopx.starter.sms.entity.SmsResponse;

import java.util.List;
import java.util.function.Consumer;

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
     * 发送短信
     * @param request 短信对象
     * @param consumer 针对第三方返回值做后续的处理
     */
    SmsResponse sendSms(SmsRequest request, Consumer<String> consumer)  throws Throwable;

    /**
     * 批量发送短信
     * @param requests 短信对象
     * @return SmsResponse
     */
    SmsResponse sendSms(List<SmsRequest> requests) throws Throwable;

    /**
     * 批量发送短信
     * @param requests 短信对象
     * @param consumer 针对第三方返回值做后续的处理
     * @return SmsResponse
     */
    SmsResponse sendSms(List<SmsRequest> requests, Consumer<String> consumer) throws Throwable;
}
