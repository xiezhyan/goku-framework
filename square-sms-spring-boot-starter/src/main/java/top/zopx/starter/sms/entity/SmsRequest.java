package top.zopx.starter.sms.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 发送短信对象
 *
 * @author sanq.Yan
 * @date 2020/11/23
 */
public class SmsRequest implements Serializable {

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 短信签名： 如 "精彩试玩"
     */
    private String signName;

    /**
     * 短信模板
     *  如果是阿里云之类的短信服务商，那么这里的模板就是在阿里云后台提交的模板编号
     *  如果是其他类型：如腾信云等短信服务商，需要自己通过url来进行处理发送的，那么这里就是具体的短信内容，那么我们在处理的时候就需要注意一点：
     *  <p>将签名和内容进行拼接</p>
     *
     */
    private String template;

    /**
     * 生成短信内容
     */
    private String content;

    /**
     * 短信模板对应参数
     */
    private Map<String, Object> templateParam;

    public SmsRequest() {
    }

    public SmsRequest(String phoneNumber, String signName, String template, Map<String, Object> templateParam) {
        this.phoneNumber = phoneNumber;
        this.signName = signName;
        this.template = template;
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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(Map<String, Object> templateParam) {
        this.templateParam = templateParam;
    }

    public String getContent() {
        this.templateParam.forEach((k, v) -> {
            this.content = this.template.replace("{" + k + "}", v.toString());
        });
        return this.content + " 【"+this.signName+"】";
    }

    public void setContent(String content) {
        this.content = content;
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
        private String template;

        /**
         * 短信模板对应参数
         */
        private Map<String, Object> templateParam = new HashMap<>();

        public SmsRequest.Build phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public SmsRequest.Build signName(String signName) {
            this.signName = signName;
            return this;
        }

        public SmsRequest.Build template(String template) {
            this.template = template;
            return this;
        }

        public SmsRequest.Build templateParam(Map<String, Object> templateParam) {
            this.templateParam = templateParam;
            return this;
        }

        public SmsRequest.Build templateParam(String key, Object value) {
            this.templateParam.put(key, value);
            return this;
        }

        public SmsRequest builder() {
            return new SmsRequest(phoneNumber, signName, template, templateParam);
        }
    }
    
    public static Build create() {
        return new SmsRequest.Build();
    }
}
