package top.zopx.goku.framework.support.mysql.binlog.entity;

import top.zopx.goku.framework.support.mysql.binlog.constant.OperateTypeCons;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/25 21:52
 */
public class TableTemplate implements Serializable {

    /**
     * 库名
     */
    private String database;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 操作字段设置
     */
    private Map<OperateTypeCons, List<String>> operateTypeMap = new HashMap<>();

    /**
     * 字段索引和字段名的映射
     */
    private Map<Integer, String> posMap = new HashMap<>();

    public TableTemplate() {
    }

    public TableTemplate(String database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<OperateTypeCons, List<String>> getOperateTypeMap() {
        return operateTypeMap;
    }

    public void setOperateTypeMap(Map<OperateTypeCons, List<String>> operateTypeMap) {
        this.operateTypeMap = operateTypeMap;
    }

    public Map<Integer, String> getPosMap() {
        return posMap;
    }

    public void setPosMap(Map<Integer, String> posMap) {
        this.posMap = posMap;
    }
}
