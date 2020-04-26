package top.zopx.starter.sms.entity;

import lombok.*;
import top.zopx.starter.tools.tools.json.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * top.zopx.starter.sms.entity.SmsRequest
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest {

    private static final Map<String, String> PARAMS = new HashMap<>();

    private String toTel;

    //sms1086使用
    private String message;

    //阿里云短信使用
    private String signName;            //签名
    private Map<String, String> map;    //参数
    private String templateCode;        //模板ID

    public String getMessage() {

        String content = message + "【" + signName + "】";

        if (map == null || map.isEmpty())
            return content;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            content = content.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        return content;
    }

    public String getTemplateParam() {
        if (map == null || map.isEmpty())
            return "";
        return JsonUtil.obj2Json(map);
    }

    public static class Builder {
        private static final Map<String, String> PARAMS = new HashMap<>();

        private String toTel;       //手机号
        private String message;     //短信内容

        private String signName;    //短信签名
        private String templateCode;        //模板ID 阿里云使用

        public Builder setToTel(String toTel) {
            this.toTel = toTel;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setSignName(String signName) {
            this.signName = signName;
            return this;
        }

        public Builder setTemplateCode(String templateCode) {
            this.templateCode = templateCode;
            return this;
        }

        public Builder setMap(Map<String, String> map) {
            PARAMS.putAll(map);
            return this;
        }

        public Builder setParam(String key, String value) {
            PARAMS.put(key, value);
            return this;
        }

        public SmsRequest builder() {
            return new SmsRequest(toTel, message, signName, PARAMS, templateCode);
        }
    }
}
