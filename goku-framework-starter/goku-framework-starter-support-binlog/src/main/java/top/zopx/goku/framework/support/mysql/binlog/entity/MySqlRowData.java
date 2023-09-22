package top.zopx.goku.framework.support.mysql.binlog.entity;

import top.zopx.goku.framework.support.mysql.binlog.constant.OperateTypeCons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/25 22:46
 */
public class MySqlRowData implements Serializable {

    private String database;

    private String tableName;

    private OperateTypeCons operateTypeCons;

    private List<Map<String, String>> fieldValueMap = new ArrayList<>();

    public MySqlRowData(String database, String tableName, OperateTypeCons operateTypeCons, List<Map<String, String>> fieldValueMap) {
        this.database = database;
        this.tableName = tableName;
        this.operateTypeCons = operateTypeCons;
        this.fieldValueMap = fieldValueMap;
    }

    public MySqlRowData() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public OperateTypeCons getOperateTypeCons() {
        return operateTypeCons;
    }

    public void setOperateTypeCons(OperateTypeCons operateTypeCons) {
        this.operateTypeCons = operateTypeCons;
    }

    public List<Map<String, String>> getFieldValueMap() {
        return fieldValueMap;
    }

    public void setFieldValueMap(List<Map<String, String>> fieldValueMap) {
        this.fieldValueMap = fieldValueMap;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "MySqlRowData{" +
                "database='" + database + '\'' +
                ", tableName='" + tableName + '\'' +
                ", operateTypeCons=" + operateTypeCons +
                ", fieldValueMap=" + fieldValueMap +
                '}';
    }
}
