package top.zopx.goku.framework.support.mysql.binlog.client;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.support.mysql.binlog.entity.BinlogRowData;
import top.zopx.goku.framework.support.mysql.binlog.entity.TableTemplate;
import top.zopx.goku.framework.support.mysql.binlog.send.ISendListener;
import top.zopx.goku.framework.support.mysql.binlog.template.ParseTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/24 22:45
 */
@Component
public class BinlogClientEventListener implements BinaryLogClient.EventListener {

    @SuppressWarnings("all")
    private static final List<EventType> EXCLUDE_POSITION_EVENT_TYPE = new ArrayList<EventType>(2) {{
        add(EventType.FORMAT_DESCRIPTION);
        add(EventType.HEARTBEAT);
    }};

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

        doSavePositionEvent(event);

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
        if (StringUtils.isBlank(database) || StringUtils.isBlank(tableName)) {
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


    private void doSavePositionEvent(Event event) {
        EventType type = event.getHeader().getEventType();

        if (!EXCLUDE_POSITION_EVENT_TYPE.contains(type)) {
            //处理rotate事件,这里会替换调binlog fileName
            if (event.getHeader().getEventType().equals(EventType.ROTATE)) {
                RotateEventData rotateEventData = (RotateEventData) event.getData();
                LOGGER.debug(
                        "binlogFile = {}, binlogPosition = {}, serverId = {}",
                        rotateEventData.getBinlogFilename(),
                        rotateEventData.getBinlogPosition(),
                        event.getHeader().getServerId()
                );
                // rotateEventData.getBinlogFilename()
                // rotateEventData.getBinlogPosition()
                // event.getHeader().getServerId()
            } else {
                //统一处理事件对应的binlog position
                // 从Redis中取出
                EventHeaderV4 eventHeaderV4 = (EventHeaderV4) event.getHeader();
                LOGGER.debug(
                        "binlogPosition = {}, serverId = {}",
                        eventHeaderV4.getPosition(),
                        event.getHeader().getServerId()
                );
                // eventHeaderV4.getPosition()
                // event.getHeader().getServerId()
            }
            // save to redis
        }
    }

}
