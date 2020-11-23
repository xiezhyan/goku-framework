package top.zopx.starter.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import org.springframework.util.CollectionUtils;
import top.zopx.starter.sms.entity.SmsRequest;
import top.zopx.starter.sms.entity.SmsResponse;
import top.zopx.starter.sms.properties.SmsProperties;
import top.zopx.starter.sms.service.ISmsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 阿里云 发送短信
 * @author sanq.Yan
 * @date 2020/11/23
 */
public class ALiYunSmsServiceImpl implements ISmsService {

    @Resource
    private IAcsClient acsClient;
    @Resource
    private SmsProperties smsProperties;

    @Override
    public SmsResponse sendSms(SmsRequest request) throws Throwable {

        if (smsProperties.getSmsLi().getOpen()) {

            if (null == request || null == request.getSmsALiYunRequest())
                return SmsResponse.error();

            CommonRequest req = new CommonRequest();
            // 固定参数
            req.setSysMethod(MethodType.POST);
            req.setSysDomain("dysmsapi.aliyuncs.com");
            req.setSysVersion("2017-05-25");
            req.setSysAction("SendSms");

            req.putQueryParameter("RegionId", smsProperties.getSmsLi().getRegionId());
            req.putQueryParameter("PhoneNumbers", request.getSmsALiYunRequest().getPhoneNumber());
            req.putQueryParameter("SignName", request.getSmsALiYunRequest().getSignName());
            req.putQueryParameter("TemplateCode", request.getSmsALiYunRequest().getTemplateCode());
            req.putQueryParameter("TemplateParam", JSON.toJSONString(request.getSmsALiYunRequest().getTemplateParam()));

            CommonResponse response = acsClient.getCommonResponse(req);

            return new SmsResponse(response.getData(), response.getHttpStatus());
        }
        return SmsResponse.error();
    }

    @Override
    public SmsResponse sendSms(List<SmsRequest> requests) throws Throwable {

        if (smsProperties.getSmsLi().getOpen()) {

            if (CollectionUtils.isEmpty(requests))
                return SmsResponse.error();

            List<String> phoneNumberJson = new ArrayList<>(requests.size());
            List<String> signNameJson = new ArrayList<>(requests.size());
            List<String> templateCode = new ArrayList<>(requests.size());
            List<Map<String, Object>> templateParamJson = new ArrayList<>(requests.size());

            requests.forEach(request -> {
                if (null != request.getSmsALiYunRequest()) {
                    phoneNumberJson.add(request.getSmsALiYunRequest().getPhoneNumber());
                    signNameJson.add(request.getSmsALiYunRequest().getSignName());
                    templateCode.add(request.getSmsALiYunRequest().getTemplateCode());
                    templateParamJson.add(request.getSmsALiYunRequest().getTemplateParam());
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
            return new SmsResponse(response.getData(), response.getHttpStatus());
        }

        return SmsResponse.error();
    }
}
