package top.zopx.starter.sms.providers.cloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.zopx.starter.sms.entity.SmsRequest;
import top.zopx.starter.sms.entity.SmsResponse;
import top.zopx.starter.sms.properties.SmsProperties;
import top.zopx.starter.sms.service.ISmsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 阿里云 发送短信
 * @author sanq.Yan
 * @date 2020/11/23
 */
public class CloudSmsServiceImpl implements ISmsService {

    @Resource
    private IAcsClient acsClient;
    @Resource
    private SmsProperties smsProperties;

    @Override
    public SmsResponse sendSms(SmsRequest request,  Consumer<String> consumer) throws Throwable {
        if (smsProperties.getSmsLi().getOpen()) {

            if (null == request) {
                return SmsResponse.error();
            }

            String signName = smsProperties.getSmsLi().getSignName();

            if (!StringUtils.hasText(signName)) {
                signName = request.getSignName();
            }

            CommonRequest req = new CommonRequest();
            // 固定参数
            req.setSysMethod(MethodType.POST);
            req.setSysDomain("dysmsapi.aliyuncs.com");
            req.setSysVersion("2017-05-25");
            req.setSysAction("SendSms");

            req.putQueryParameter("RegionId", smsProperties.getSmsLi().getRegionId());
            req.putQueryParameter("PhoneNumbers", request.getPhoneNumber());
            req.putQueryParameter("SignName", signName);
            req.putQueryParameter("TemplateCode", request.getTemplate());
            req.putQueryParameter("TemplateParam", JSON.toJSONString(request.getTemplateParam()));

            CommonResponse response = acsClient.getCommonResponse(req);

            if (null != consumer) {
                consumer.accept(JSON.toJSONString(response));
            }

            return new SmsResponse(response.getData(), response.getHttpStatus());
        }
        return SmsResponse.error();
    }

    @Override
    public SmsResponse sendSms(SmsRequest request) throws Throwable {
        return sendSms(request, null);
    }

    @Override
    public SmsResponse sendSms(List<SmsRequest> requests, Consumer<String> consumer) throws Throwable {

        if (smsProperties.getSmsLi().getOpen()) {

            if (CollectionUtils.isEmpty(requests)) {
                return SmsResponse.error();
            }

            List<String> phoneNumberJson = new ArrayList<>(requests.size());
            List<String> signNameJson = new ArrayList<>(requests.size());
            List<String> templateCode = new ArrayList<>(requests.size());
            List<Map<String, Object>> templateParamJson = new ArrayList<>(requests.size());

            requests.forEach(request -> {
                if (null != request) {
                    phoneNumberJson.add(request.getPhoneNumber());
                    signNameJson.add(request.getSignName());
                    templateCode.add(request.getTemplate());
                    templateParamJson.add(request.getTemplateParam());
                }
            });

            CommonRequest request = new CommonRequest();
            // 固定参数
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendBatchSms");

            request.putQueryParameter("RegionId", smsProperties.getSmsLi().getRegionId());
            request.putQueryParameter("PhoneNumberJson", JSON.toJSONString(phoneNumberJson));
            request.putQueryParameter("SignNameJson", JSON.toJSONString(signNameJson));
            request.putQueryParameter("TemplateCode", JSON.toJSONString(templateCode));
            request.putQueryParameter("TemplateParamJson", JSON.toJSONString(templateParamJson));

            CommonResponse response = acsClient.getCommonResponse(request);

            if (null != consumer) {
                consumer.accept(JSON.toJSONString(response));
            }

            return new SmsResponse(response.getData(), response.getHttpStatus());
        }

        return SmsResponse.error();
    }

    @Override
    public SmsResponse sendSms(List<SmsRequest> requests) throws Throwable {
        return sendSms(requests, null);
    }
}
