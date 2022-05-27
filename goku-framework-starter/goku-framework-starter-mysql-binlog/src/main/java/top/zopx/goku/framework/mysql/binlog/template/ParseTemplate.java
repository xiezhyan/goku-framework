package top.zopx.goku.framework.mysql.binlog.template;

import top.zopx.goku.framework.mysql.binlog.constant.OperateTypeCons;
import top.zopx.goku.framework.mysql.binlog.entity.TableTemplate;
import top.zopx.goku.framework.mysql.binlog.entity.TemplateSchema;
import top.zopx.goku.framework.tools.exceptions.BusException;

import java.util.*;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/25 21:49
 */
public final class ParseTemplate {

    private ParseTemplate() {
    }

    /**
     * 表名和TableTemplate的映射关系
     */
    public static final Map<String, TableTemplate> MAP = new HashMap<>();

    public static void parse(TemplateSchema templateSchema) {
        if (Objects.isNull(templateSchema)) {
            throw new BusException("json格式解析内容异常");
        }

        templateSchema.getSchema().forEach(schema ->
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
