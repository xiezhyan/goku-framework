package top.zopx.starter.step.up.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zopx.starter.step.up.utils.S;

/**
 * top.zopx.starter.step.up.entity.Column
 *
 * @author sanq.Yan
 * @date 2020/4/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Column {

    private String columnName;

    private String columnComment;

    private String columnKey;

    private String dataType;

    private Boolean priKey;

    private String javaType;

    private String javaColumnName;

    public String getJavaColumnName() {
        int i = this.columnName.indexOf("_");

        // 处理字段中的下划线 并转换格式
        this.javaColumnName = i > 0 ? S.replaceChar(this.columnName, "_") : this.columnName;

        return javaColumnName;
    }

    public Boolean getPriKey() {
        return "PRI".equals(columnKey);
    }

    public String getJavaType() {
        if (this.dataType.equalsIgnoreCase("varchar")
                || this.dataType.equalsIgnoreCase("text")
                || this.dataType.equalsIgnoreCase("longtext")) {
            this.javaType = "String";
        } else if (this.dataType.equalsIgnoreCase("int")
                || this.dataType.equalsIgnoreCase("tinyint")
                || this.dataType.equalsIgnoreCase("smallint")
                || this.dataType.equalsIgnoreCase("mediumint")) {
            this.javaType = "Integer";
        } else if (this.dataType.equalsIgnoreCase("date")
                || this.dataType.equalsIgnoreCase("time")
                || this.dataType.equalsIgnoreCase("datetime")
                || this.dataType.equalsIgnoreCase("timestamp")) {
            this.javaType = "Date";
        } else if (this.dataType.equalsIgnoreCase("double")) {
            this.javaType = "Double";
        } else if (this.dataType.equalsIgnoreCase("long")
                || this.dataType.equalsIgnoreCase("bigint")) {
            this.javaType = "Long";
        } else if (this.dataType.equalsIgnoreCase("decimal")) {
            this.javaType = "BigDecimal";
        } else if (this.dataType.equalsIgnoreCase("float")) {
            this.javaType = "Float";
        } else if (this.dataType.equalsIgnoreCase("char")) {
            this.javaType = "Character";
        }
        return javaType;
    }
}
