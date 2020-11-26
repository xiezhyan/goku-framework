package top.zopx.starter.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zopx.starter.sms.properties.SmsProperties;
import top.zopx.starter.sms.service.ISmsService;
import top.zopx.starter.tools.basic.Response;

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
        return new Response().success();
    }

}
