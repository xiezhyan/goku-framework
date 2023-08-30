package top.zopx.goku.framework.socket.core.config.properties;

import com.zaxxer.hikari.HikariConfig;

import java.io.Serializable;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/25 20:25
 */
public class BootstrapDatasource implements Serializable {

    private String driverClassName;
    private String jdbc;
    private String userName;
    private String password;
    private HikariConfig hikari = new HikariConfig();

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getJdbc() {
        return jdbc;
    }

    public void setJdbc(String jdbc) {
        this.jdbc = jdbc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HikariConfig getHikari() {
        return hikari;
    }

    public void setHikari(HikariConfig hikari) {
        this.hikari = hikari;
    }
}
