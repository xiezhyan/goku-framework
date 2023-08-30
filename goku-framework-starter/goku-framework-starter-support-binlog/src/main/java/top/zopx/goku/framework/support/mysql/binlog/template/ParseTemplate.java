package top.zopx.goku.framework.support.mysql.binlog.template;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.support.mysql.binlog.constant.OperateTypeCons;
import top.zopx.goku.framework.support.mysql.binlog.entity.TableTemplate;
import top.zopx.goku.framework.support.mysql.binlog.entity.Template;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/25 21:49
 */
@Component
public class ParseTemplate {

    private ParseTemplate() {
    }

    /**
     * 表名和TableTemplate的映射关系
     */
    public static final Map<String, TableTemplate> MAP = new HashMap<>();

    public static void parse(List<Template> templates) {
        if (CollectionUtils.isEmpty(templates)) {
            throw new BusException("json格式解析内容异常", IBus.ERROR_CODE);
        }

        templates.forEach(schema ->
                schema.getTableList().forEach(jsonTable -> {
                    final TableTemplate tableTemplate = new TableTemplate(schema.getDatabase(), jsonTable.getTableName());
                    MAP.put(jsonTable.getTableName(), tableTemplate);

                    final Map<OperateTypeCons, List<String>> operateTypeMap = tableTemplate.getOperateTypeMap();

                    jsonTable.getInsert().forEach(column ->
                            operateTypeMap.computeIfAbsent(OperateTypeCons.ADD, cons -> new ArrayList<>()).add(column.getColumn())
                    );
                    jsonTable.getUpdate().forEach(column ->
                            operateTypeMap.computeIfAbsent(OperateTypeCons.UPDATE, cons -> new ArrayList<>()).add(column.getColumn())
                    );
                    jsonTable.getDelete().forEach(column ->
                            operateTypeMap.computeIfAbsent(OperateTypeCons.DELETE, cons -> new ArrayList<>()).add(column.getColumn())
                    );
                })
        );

        LoadMeta.loadMeta();
    }
}
