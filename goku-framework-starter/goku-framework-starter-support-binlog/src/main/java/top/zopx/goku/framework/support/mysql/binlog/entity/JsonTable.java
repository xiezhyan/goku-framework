package top.zopx.goku.framework.support.mysql.binlog.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/25 21:38
 */
public class JsonTable implements Serializable {

    private String tableName;

    private List<Column> insert;

    private List<Column> update;

    private List<Column> delete;

    public JsonTable() {
    }

    public JsonTable(String tableName, List<Column> insert, List<Column> update, List<Column> delete) {
        this.tableName = tableName;
        this.insert = insert;
        this.update = update;
        this.delete = delete;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getInsert() {
        return insert;
    }

    public void setInsert(List<Column> insert) {
        this.insert = insert;
    }

    public List<Column> getUpdate() {
        return update;
    }

    public void setUpdate(List<Column> update) {
        this.update = update;
    }

    public List<Column> getDelete() {
        return delete;
    }

    public void setDelete(List<Column> delete) {
        this.delete = delete;
    }

    public static class Column implements Serializable {

        /**
         * 字段
         */
        private String column;

        public Column() {
        }

        public Column(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        @Override
        public String toString() {
            return "Column{" +
                    "column='" + column + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "JsonTable{" +
                "tableName='" + tableName + '\'' +
                ", insert=" + insert +
                ", update=" + update +
                ", delete=" + delete +
                '}';
    }
}
