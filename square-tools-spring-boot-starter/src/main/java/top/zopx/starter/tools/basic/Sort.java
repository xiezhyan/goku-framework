package top.zopx.starter.tools.basic;

import top.zopx.starter.tools.tools.strings.StringUtil;

/**
 * top.zopx.starter.tools.basic.Sort
 *
 * @author sanq.Yan
 * @date 2020/5/12
 */
public abstract class Sort {
    private String field;
    private String sorted;

    public Sort(String field, String sorted) {
        this.field = field;
        this.sorted = sorted;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSorted() {
        if (StringUtil.isNotBlank(sorted)) {
            return "ASC";
        }
        return sorted;
    }

    public void setSorted(String sorted) {
        this.sorted = sorted;
    }
}
