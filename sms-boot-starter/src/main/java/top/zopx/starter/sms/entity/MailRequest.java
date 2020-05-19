package top.zopx.starter.sms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * top.zopx.starter.sms.entity.MailRequest
 *
 * @author sanq.Yan
 * @date 2020/5/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailRequest {

    /**
     * spring:
     *   mail:
     *     # 邮件服务地址
     *     host: smtp.163.com
     *     # 端口,可不写默认
     *     port: 25
     *     # 编码格式
     *     default-encoding: utf-8
     *     # 用户名
     *     username: xxx@163.com
     *     # 授权码，就是我们刚才准备工作获取的代码
     *     password: xxx
     *     # 其它参数
     *     properties:
     *       mail:
     *         smtp:
     *           # 如果是用 SSL 方式，需要配置如下属性,使用qq邮箱的话需要开启
     *           ssl:
     *             enable: true
     *             required: true
     *           # 邮件接收时间的限制，单位毫秒
     *           timeout: 10000
     *           # 连接时间的限制，单位毫秒
     *           connectiontimeout: 10000
     *           # 邮件发送时间的限制，单位毫秒
     *           writetimeout: 10000
     */
    private String from;    // 发送者

    private String to;      // 接收者

    private String subject; // 主题

    private String content; // 内容

    private MailType type = MailType.SIMPLE;  // 内容类型

    public static enum MailType {
        SIMPLE,
        HTML,
        MIME,
        INLINE_MIME
    }
}

