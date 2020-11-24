package top.zopx.starter.sms.providers.a_li.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云短信发送对象
 *
 * @author sanq.Yan
 * @date 2020/11/23
 */
public class SmsLiYunRequest implements Serializable {

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
    private Map<String, Object> templateParam;

    public SmsLiYunRequest() {
    }

    public SmsLiYunRequest(String phoneNumber, String signName, String templateCode, Map<String, Object> templateParam) {
        this.phoneNumber = phoneNumber;
        this.signName = signName;
        this.templateCode = templateCode;
        this.templateParam = templateParam;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Map<String, Object> getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(Map<String, Object> templateParam) {
        this.templateParam = templateParam;
    }

    public static class Build {
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

        public Build phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Build signName(String signName) {
            this.signName = signName;
            return this;
        }

        public Build templateCode(String templateCode) {
            this.templateCode = templateCode;
            return this;
        }

        public Build templateParam(Map<String, Object> templateParam) {
            this.templateParam = templateParam;
            return this;
        }

        public Build templateParam(String key, Object value) {
            this.templateParam.put(key, value);
            return this;
        }

        public SmsLiYunRequest builder() {
            return new SmsLiYunRequest(phoneNumber, signName, templateCode, templateParam);
        }
    }
}
