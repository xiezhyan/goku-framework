package top.zopx.square.service.impl;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import top.zopx.starter.sms.entity.SmsRequest;
import top.zopx.starter.sms.service.ISmsService;

import javax.annotation.Resource;

/**
 * @author sanq.Yan
 * @date 2021/4/17
 */
@Service
public class SmsServiceImpl {

    @Resource
    private ISmsService smsService;

    public void sendVer() throws Throwable {
        smsService.sendSms(
                SmsRequest.create()
                        .phoneNumber("15110148609")
                        .template("SMS_191190401")
                        .templateParam("code", RandomUtils.nextLong(1000, 9999))
                        .builder(),
                System.out::println
        );
    }
}
