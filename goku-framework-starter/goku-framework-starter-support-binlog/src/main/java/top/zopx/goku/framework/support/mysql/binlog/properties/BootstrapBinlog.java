package top.zopx.goku.framework.support.mysql.binlog.properties;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

import static top.zopx.goku.framework.support.mysql.binlog.properties.BootstrapBinlog.PREFIX;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/24 22:21
 */
@Configuration
@ConfigurationProperties(value = PREFIX)
public class BootstrapBinlog implements Serializable {
    public static final String PREFIX = "goku.mysql";

    /**
     * 定义需要处理的库表和字段信息
     * 默认路径：resources/template.json
     */
    private String template = "template.json";

    private Binlog binlog = new Binlog();

    public static class Binlog implements Serializable {

        /**
         * MySQL host
         */
        private String host = "127.0.0.1";

        /**
         * MySQL 端口
         */
        private Integer port = 3306;

        /**
         * 账户
         */
        private String username;
        /**
         * 密码
         */
        private String password;

        /**
         * binlog名称
         */
        private String binlogName;

        /**
         * position
         */
        private Long position = -1L;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getBinlogName() {
            return binlogName;
        }

        public void setBinlogName(String binlogName) {
            this.binlogName = binlogName;
        }

        public Long getPosition() {
            return position;
        }

        public void setPosition(Long position) {
            this.position = position;
        }

        public Long getServerId() {
            return RandomUtils.nextLong(1L, 65535L);
        }
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Binlog getBinlog() {
        return binlog;
    }

    public void setBinlog(Binlog binlog) {
        this.binlog = binlog;
    }
}
