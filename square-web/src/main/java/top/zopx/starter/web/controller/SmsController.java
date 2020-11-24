package top.zopx.starter.web.controller;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zopx.starter.sms.entity.SmsRequest;
import top.zopx.starter.sms.entity.SmsResponse;
import top.zopx.starter.sms.properties.SmsProperties;
import top.zopx.starter.sms.providers.a_li.entity.SmsLiYunRequest;
import top.zopx.starter.sms.service.ISmsService;
import top.zopx.starter.tools.basic.Response;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.web.LogUtil;

import javax.annotation.Resource;

/**
 * @author sanq.Yan
 * @date 2020/11/23
 */
@RestController
public class SmsController {

    @Resource
    private SmsProperties smsProperties;
    @Autowired
    private ISmsService smsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsController.class);

    @GetMapping("/getSmsConfig")
    public void getSmsProperties() {
        LOGGER.info("{}", smsProperties.getSmsLi().getAccessKeyId());

        LOGGER.info("{}", smsService);
    }

    @GetMapping("/sendSms")
    public Response sendSms() {
        SmsRequest smsRequest = SmsRequest.create();

        smsRequest.setSmsLiYunRequest(
                new SmsLiYunRequest.Build()
                        .phoneNumber("15110148609")
                        .templateCode("SL_10000")
                        .templateParam("code", RandomUtils.nextInt(1000, 9999))
                        .builder()
        );

        SmsResponse smsResponse = null;
        try {
            smsResponse = smsService.sendSms(smsRequest);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new BusException(throwable.getMessage());
        }

        LogUtil.info("{}:{}", smsResponse.getDesc(), smsResponse.getStatus());
        return new Response().success();
    }

}
