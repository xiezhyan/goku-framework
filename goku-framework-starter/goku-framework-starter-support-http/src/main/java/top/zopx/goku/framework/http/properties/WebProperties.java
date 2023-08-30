package top.zopx.goku.framework.http.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/31 07:31
 */
@ConfigurationProperties(value = "goku.web")
public class WebProperties implements Serializable {

    private Api app;

    private Api admin;

    public Api getApp() {
        return app;
    }

    public void setApp(Api app) {
        this.app = app;
    }

    public Api getAdmin() {
        return admin;
    }

    public void setAdmin(Api admin) {
        this.admin = admin;
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
