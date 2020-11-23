package top.zopx.starter.sms.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云短信发送对象
 *
 * @author sanq.Yan
 * @date 2020/11/23
 */
public class SmsALiYunRequest implements Serializable {

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 短信模板
     */
    private String templateCode;

    /**
     * 短信模板对应参数
     */
    private Map<String, Object> templateParam = new HashMap<>();

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public SmsALiYunRequest setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getSignName() {
        return signName;
    }

    public SmsALiYunRequest setSignName(String signName) {
        this.signName = signName;
        return this;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public SmsALiYunRequest setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
        return this;
    }

    public Map<String, Object> getTemplateParam() {
        return templateParam;
    }

    public SmsALiYunRequest setTemplateParam(Map<String, Object> templateParam) {
        this.templateParam = templateParam;
        return this;
    }

    public SmsALiYunRequest setTemplateParam(String key, Object value) {
        this.templateParam.put(key, value);
        return this;
    }

    public static SmsALiYunRequest create() {
        return new SmsALiYunRequest();
    }
}
