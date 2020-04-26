package top.zopx.starter.sms.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import lombok.SneakyThrows;
import top.zopx.starter.sms.entity.SmsRequest;
import top.zopx.starter.sms.entity.SmsResponse;
import top.zopx.starter.sms.service.SmsService;

import javax.annotation.Resource;

/**
 * top.zopx.starter.sms.service.AliYunSmsServiceImpl
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */

public class AliYunSmsServiceImpl implements SmsService {

    @Resource
    private IAcsClient acsClient;

    @SneakyThrows
    @Override
    public SmsResponse sendSms(SmsRequest smsRequest) {
        SendSmsRequest request = new SendSmsRequest();

        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号
        request.setPhoneNumbers(smsRequest.getToTel());
        //必填:短信签名
        request.setSignName(smsRequest.getSignName());
        //必填:短信模板
        request.setTemplateCode(smsRequest.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(smsRequest.getTemplateParam());

        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return SmsResponse.builder()
                .code(sendSmsResponse.getCode())
                .message(sendSmsResponse.getMessage())
                .build();
    }
}
