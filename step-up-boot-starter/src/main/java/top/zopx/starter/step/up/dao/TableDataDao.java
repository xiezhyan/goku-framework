package top.zopx.starter.step.up.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import top.zopx.starter.step.up.config.StepUpProperties;
import top.zopx.starter.step.up.constant.TableData;
import top.zopx.starter.step.up.entity.Column;
import top.zopx.starter.step.up.entity.Table;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * top.zopx.starter.step.up.dao
 *
 * @author sanq.Yan
 * @date 2020/4/28
 */
@Slf4j
@Component
public class TableDataDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private StepUpProperties stepUpProperties;

    public void init() {
        getTable().forEach(
                table -> TableData.MAP.put(
                        table,
                        getColumnByTableName(table.getTableName())
                )
        );
    }

    private List<Table> getTable() {
        String sql = "SELECT TABLE_NAME, TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ?";

        List<Table> tables = new ArrayList<>();

        jdbcTemplate.query(sql,
                new Object[]{stepUpProperties.getSchema()},
                (rs, i) -> {
                    Table table = Table.builder()
                            .tableName(rs.getString("TABLE_NAME"))
                            .tableComment(rs.getString("TABLE_COMMENT"))
                            .build();
                    table.updateJavaName(stepUpProperties.getPrefix());
                    tables.add(table);
                    return null;
                });
        return tables;
    }

    private List<Column> getColumnByTableName(String tableName) {
        String sql = "SELECT COLUMN_NAME, COLUMN_COMMENT,COLUMN_KEY,DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";

        List<Column> columns = new ArrayList<>();
        jdbcTemplate.query(
                sql,
                new Object[]{stepUpProperties.getSchema(), tableName},
                (rs, i) -> {
                    Column column = new Column();
                    column.setColumnName(rs.getString("COLUMN_NAME"));
                    column.setColumnComment(rs.getString("COLUMN_COMMENT"));
                    column.setColumnKey(rs.getString("COLUMN_KEY"));
                    column.setDataType(rs.getString("DATA_TYPE"));
                    columns.add(column);
                    return null;
                }
        );

        return columns;
    }
}
