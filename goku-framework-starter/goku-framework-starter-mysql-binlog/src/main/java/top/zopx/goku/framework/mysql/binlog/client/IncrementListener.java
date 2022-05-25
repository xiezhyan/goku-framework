package top.zopx.goku.framework.mysql.binlog.client;

import com.github.shyiko.mysql.binlog.event.EventType;
import org.apache.commons.collections4.MapUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.mysql.binlog.constant.OperateTypeCons;
import top.zopx.goku.framework.mysql.binlog.entity.BinlogRowData;
import top.zopx.goku.framework.mysql.binlog.entity.MySqlRowData;
import top.zopx.goku.framework.mysql.binlog.entity.TableTemplate;
import top.zopx.goku.framework.mysql.binlog.send.ISendListener;
import top.zopx.goku.framework.mysql.binlog.send.ISender;
import top.zopx.goku.framework.mysql.binlog.template.ParseTemplate;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 俗世游子
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

    @Override
    @PostConstruct
    public void registry() {
        taskExecutor.execute(() -> {
            for (; ; ) {
                if (MapUtils.isNotEmpty(ParseTemplate.MAP)) {
                    LogHelper.getLogger(IncrementListener.class).info("registry is ok");
                    ParseTemplate.MAP.forEach((key, value) -> binlogClientEventListener.register(
                            value.getDatabase(),
                            value.getTableName(),
                            this
                    ));
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

        rowData.setTableName(table.getTableName());
        OperateTypeCons opType = OperateTypeCons.to(eventType);
        rowData.setOperateTypeCons(opType);

        // 取出模板中该操作对应的字段列表
        List<String> fieldList = table.getOperateTypeMap().get(opType);
        if (null == fieldList) {
            LogHelper.getLogger(IncrementListener.class).warn("{} not support for {}", opType, table.getTableName());
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
