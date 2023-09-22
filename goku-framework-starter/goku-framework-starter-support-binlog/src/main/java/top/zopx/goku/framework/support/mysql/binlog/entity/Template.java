package top.zopx.goku.framework.support.mysql.binlog.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/25 21:37
 */
public class Template implements Serializable {

    private String database;

    private List<JsonTable> tableList;

    public Template() {
    }

    public Template(String database, List<JsonTable> tableList) {
        this.database = database;
        this.tableList = tableList;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public List<JsonTable> getTableList() {
        return tableList;
    }

    public void setTableList(List<JsonTable> tableList) {
        this.tableList = tableList;
    }

    @Override
    public String toString() {
        return "Template{" +
                "database='" + database + '\'' +
                ", tableList=" + tableList +
                '}';
    }
}
