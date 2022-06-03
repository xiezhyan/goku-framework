package top.zopx.goku.framework.mybatis.binlog.client;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.mybatis.binlog.entity.BinlogRowData;
import top.zopx.goku.framework.mybatis.binlog.entity.TableTemplate;
import top.zopx.goku.framework.mybatis.binlog.send.ISendListener;
import top.zopx.goku.framework.mybatis.binlog.template.ParseTemplate;
import top.zopx.goku.framework.tools.util.string.StringUtil;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/24 22:45
 */
@Component
public class BinlogClientEventListener implements BinaryLogClient.EventListener{

    private String database;
    private String tableName;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BinlogClientEventListener.class);

    private final Map<String, ISendListener> listenerMap = new HashMap<>();

    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    public void register(String database, String tableName, ISendListener listener) {
        LOGGER.info("register : {}-{}", database, tableName);
        this.listenerMap.put(genKey(database, tableName), listener);
    }

    @Override
    public void onEvent(Event event) {

        EventType type = event.getHeader().getEventType();
        LOGGER.debug("event type: {}", type);

        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.database = data.getDatabase();
            return;
        }

        if (type != EventType.EXT_UPDATE_ROWS
                && type != EventType.EXT_WRITE_ROWS
                && type != EventType.EXT_DELETE_ROWS) {
            return;
        }

        // 表名和库名是否已经完成填充
        if (StringUtil.isEmpty(database) || StringUtil.isEmpty(tableName)) {
            LOGGER.error("no meta data event");
            return;
        }

        // 找出对应表有兴趣的监听器
        String key = genKey(this.database, this.tableName);
        ISendListener listener = this.listenerMap.get(key);
        if (null == listener) {
            LOGGER.debug("skip {}", key);
            return;
        }

        LOGGER.info("trigger event: {}", type.name());

        try {
            BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return;
            }
            rowData.setEventType(type);
            listener.onEvent(rowData);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.database = "";
            this.tableName = "";
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.emptyList();
    }

    private BinlogRowData buildRowData(EventData eventData) {

        TableTemplate table = ParseTemplate.MAP.get(tableName);

        if (null == table) {
            LOGGER.warn("table {} not found", tableName);
            return null;
        }

        List<Map<String, String>> afterMapList = new ArrayList<>();

        for (Serializable[] after : getAfterValues(eventData)) {

            Map<String, String> afterMap = new HashMap<>();

            int colLen = after.length;

            for (int ix = 0; ix < colLen; ++ix) {

                // 取出当前位置对应的列名
                String colName = table.getPosMap().get(ix);

                // 如果没有则说明不关心这个列
                if (null == colName) {
                    LOGGER.debug("ignore position: {}", ix);
                    continue;
                }

                String colValue = after[ix].toString();
                afterMap.put(colName, colValue);
            }

            afterMapList.add(afterMap);
        }

        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);

        return rowData;
    }
}
