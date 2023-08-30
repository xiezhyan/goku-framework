package top.zopx.goku.framework.support.mysql.binlog.client;

import com.github.shyiko.mysql.binlog.event.EventType;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.support.mysql.binlog.constant.OperateTypeCons;
import top.zopx.goku.framework.support.mysql.binlog.entity.BinlogRowData;
import top.zopx.goku.framework.support.mysql.binlog.entity.MySqlRowData;
import top.zopx.goku.framework.support.mysql.binlog.entity.TableTemplate;
import top.zopx.goku.framework.support.mysql.binlog.send.ISendListener;
import top.zopx.goku.framework.support.mysql.binlog.send.ISender;
import top.zopx.goku.framework.support.mysql.binlog.template.ParseTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/25 22:39
 */
@Component
public class IncrementListener implements ISendListener {

    @Resource
    private ISender sender;
    @Resource
    private BinlogClientEventListener binlogClientEventListener;
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    private static final Logger LOGGER = LoggerFactory.getLogger(IncrementListener.class);

    @Override
    @PostConstruct
    public void registry() {
        taskExecutor.execute(() -> {
            for (; ; ) {
                if (MapUtils.isNotEmpty(ParseTemplate.MAP)) {
                    LOGGER.info("registry is ok");
                    ParseTemplate.MAP.forEach((key, value) -> binlogClientEventListener.register(
                            value.getDatabase(),
                            value.getTableName(),
                            this
                    ));
                    break;
                }
            }
        });
    }

    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        // 包装成最后需要投递的数据
        MySqlRowData rowData = new MySqlRowData();

        rowData.setDatabase(table.getDatabase());
        rowData.setTableName(table.getTableName());
        OperateTypeCons opType = OperateTypeCons.to(eventType);
        rowData.setOperateTypeCons(opType);

        // 取出模板中该操作对应的字段列表
        List<String> fieldList = table.getOperateTypeMap().get(opType);
        if (null == fieldList) {
            LOGGER.error("{} not support for {}", opType, table.getTableName());
            return;
        }

        for (Map<String, String> after : eventData.getAfter()) {
            Map<String, String> afterMap = new HashMap<>();

            for (Map.Entry<String, String> entry : after.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();

                afterMap.put(colName, colValue);
            }

            rowData.getFieldValueMap().add(afterMap);
        }

        sender.sender(rowData);
    }
}
