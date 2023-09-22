package top.zopx.goku.framework.support.mysql.binlog.entity;

import com.github.shyiko.mysql.binlog.event.EventType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/25 22:33
 */
public class BinlogRowData implements Serializable {

    private TableTemplate table;

    private EventType eventType;

    private List<Map<String, String>> after;

    private List<Map<String, String>> before;

    public TableTemplate getTable() {
        return table;
    }

    public void setTable(TableTemplate table) {
        this.table = table;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public List<Map<String, String>> getAfter() {
        return after;
    }

    public void setAfter(List<Map<String, String>> after) {
        this.after = after;
    }

    public List<Map<String, String>> getBefore() {
        return before;
    }

    public void setBefore(List<Map<String, String>> before) {
        this.before = before;
    }

    @Override
    public String toString() {
        return "BinlogRowData{" +
                "table=" + table +
                ", eventType=" + eventType +
                ", after=" + after +
                ", before=" + before +
                '}';
    }
}
