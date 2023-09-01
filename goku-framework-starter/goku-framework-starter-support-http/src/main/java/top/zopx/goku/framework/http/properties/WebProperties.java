package top.zopx.goku.framework.http.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/31 07:31
 */
@ConfigurationProperties(value = "goku.web")
public class WebProperties implements Serializable {

    private Map<String, Api>  match;

    public Map<String, Api> getMatch() {
        return match;
    }

    public void setMatch(Map<String, Api> match) {
        this.match = match;
    }

    public static class Api implements Serializable {
        private String prefix;

        private String controller;

        public Api() {
        }

        public Api(String prefix, String controller) {
            this.prefix = prefix;
            this.controller = controller;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getController() {
            return controller;
        }

        public void setController(String controller) {
            this.controller = controller;
        }
    }
}
