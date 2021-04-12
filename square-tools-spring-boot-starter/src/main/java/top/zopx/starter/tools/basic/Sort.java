package top.zopx.starter.tools.basic;

/**
 * top.zopx.starter.tools.basic.Sort
 *
 * @author sanq.Yan
 * @date 2020/5/12
 */
public class Sort {
    private String field;
    private String sorted = "asc";

    public Sort() {
    }

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
        return sorted;
    }

    public void setSorted(String sorted) {
        this.sorted = sorted;
    }
}
