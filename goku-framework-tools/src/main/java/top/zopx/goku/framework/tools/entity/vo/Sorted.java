package top.zopx.goku.framework.tools.entity.vo;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 排序对象
 *
 * @author Mr.Xie
 * @date 2020/1/26
 */
public class Sorted implements Serializable {

    /**
     * 字段
     */
    private String field = "id";

    /**
     * 排序方式
     */
    private String orderBy = "asc";

    /**
     * 转换之后字段
     */
    private transient String lineField = "id";

    public String getField() {
        if (this.field.contains("_")) {
            Matcher matcher = LINE_PATTERN.matcher(this.field);
            while (matcher.find()) {
                String target = matcher.group(1);
                this.field = this.field.replaceAll("_" + target, target.toUpperCase());
            }
        }
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setLineField(String lineField) {
        this.lineField = lineField;
    }

    /**
     * 下划线转驼峰
     */
    private static final Pattern LINE_PATTERN = Pattern.compile("_(.)");
    /**
     * 驼峰转下划线
     */
    private static final Pattern HUMP_PATTERN = Pattern.compile("([A-Z])");

    public String getLineField() {
        this.lineField = this.field;
        Matcher matcher = HUMP_PATTERN.matcher(this.lineField);
        while (matcher.find()) {
            String target = matcher.group();
            this.lineField = this.lineField.replaceAll(target, "_" + target.toLowerCase());
        }
        return this.lineField;
    }
}
