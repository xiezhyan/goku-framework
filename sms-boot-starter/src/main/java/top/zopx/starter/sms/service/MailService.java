package top.zopx.starter.sms.service;

import top.zopx.starter.sms.entity.MailRequest;

import java.util.Map;

/**
 * top.zopx.starter.sms.service.MailService
 *
 * @author sanq.Yan
 * @date 2020/5/19
 */
public interface MailService {

    void sendMail(MailRequest request);

    void sendMail(MailRequest request, Map<String, byte[]> map);
}
