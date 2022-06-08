package top.zopx.goku.framework.mysql.binlog.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/25 21:36
 */
public class TemplateSchema implements Serializable {

    private List<Template> schema;

    public TemplateSchema() {
    }

    public TemplateSchema(List<Template> schema) {
        this.schema = schema;
    }

    public List<Template> getSchema() {
        return schema;
    }

    public void setSchema(List<Template> schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        if (Objects.isNull(this)) {
            return "";
        }
        return "TemplateSchema{" +
                "schema=" + schema +
                '}';
    }
}
