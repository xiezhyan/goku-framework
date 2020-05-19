package top.zopx.starter.sms.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.CollectionUtils;
import top.zopx.starter.sms.entity.MailRequest;
import top.zopx.starter.sms.service.MailService;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.util.Map;

/**
 * top.zopx.starter.sms.service.impl.MailServiceImpl
 *
 * @author sanq.Yan
 * @date 2020/5/19
 */
@Slf4j
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(MailRequest request) {

        if (MailRequest.MailType.SIMPLE == request.getType()) {
            // 普通邮件
            SimpleMailMessage simpleMailMessage = getSimpleMailMessage(request);

            javaMailSender.send(simpleMailMessage);
        } else if (MailRequest.MailType.HTML == request.getType()) {
            // HTML邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            getMimeMessageHelper(mimeMessage, request, null);

            javaMailSender.send(mimeMessage);
        }
    }

    @Override
    public void sendMail(MailRequest request, Map<String, byte[]> map) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        getMimeMessageHelper(mimeMessage, request, map);

        // 附件
        javaMailSender.send(mimeMessage);
    }

    @SneakyThrows
    private void getMimeMessageHelper(MimeMessage mimeMessage, MailRequest request, Map<String, byte[]> map) {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(request.getFrom());
        mimeMessageHelper.setTo(request.getTo());
        mimeMessageHelper.setSubject(request.getSubject());
        mimeMessageHelper.setText(request.getContent(), true);

        if (!CollectionUtils.isEmpty(map)) {
            if (MailRequest.MailType.MIME == request.getType()) {
                //
                for (Map.Entry<String, byte[]> entry : map.entrySet()) {

                    mimeMessageHelper.addAttachment(entry.getKey(), () -> new ByteArrayInputStream(entry.getValue()));
                }
            } else if (MailRequest.MailType.INLINE_MIME == request.getType()) {
                //
                for (Map.Entry<String, byte[]> entry : map.entrySet()) {
                    mimeMessageHelper.addInline(entry.getKey(), () -> new ByteArrayInputStream(entry.getValue()), "text/html;charset=UTF-8");
                }
            }
        }

    }

    private SimpleMailMessage getSimpleMailMessage(MailRequest request) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(request.getFrom());
        simpleMailMessage.setTo(request.getTo());
        simpleMailMessage.setSubject(request.getSubject());
        simpleMailMessage.setText(request.getContent());

        return simpleMailMessage;
    }
}
