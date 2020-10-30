package top.zopx.starter.tools.basic;

import top.zopx.starter.tools.constants.Sorted;

/**
 * top.zopx.starter.tools.basic.Sort
 *
 * @author sanq.Yan
 * @date 2020/5/12
 */
public class Sort {
    private String field;
    private Sorted sorted = Sorted.ASC;

    public Sort() {
    }

    public Sort(String field, Sorted sorted) {
        this.field = field;
        this.sorted = sorted;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Sorted getSorted() {
        return sorted;
    }

    public void setSorted(Sorted sorted) {
        this.sorted = sorted;
    }
}
