package top.zopx.goku.sdk.mysql.binlog.entity;

import top.zopx.goku.sdk.mysql.binlog.constant.OperateTypeCons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/25 22:46
 */
public class MySqlRowData implements Serializable {

    private String tableName;

    private OperateTypeCons operateTypeCons;

    private List<Map<String, String>> fieldValueMap = new ArrayList<>();

    public MySqlRowData(String tableName, OperateTypeCons operateTypeCons, List<Map<String, String>> fieldValueMap) {
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

    @Override
    public String toString() {
        return "MySqlRowData{" +
                "tableName='" + tableName + '\'' +
                ", operateTypeCons=" + operateTypeCons +
                ", fieldValueMap=" + fieldValueMap +
                '}';
    }
}
