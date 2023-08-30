package top.zopx.goku.framework.support.mysql.binlog.template;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.support.mysql.binlog.constant.OperateTypeCons;

import java.util.List;
import java.util.Objects;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/25 22:07
 */
@Component
@SuppressWarnings("all")
public class LoadMeta {

    private static JdbcTemplate jdbcTemplate;

    public LoadMeta(JdbcTemplate jdbcTemplate) {
        LoadMeta.jdbcTemplate = jdbcTemplate;
    }

    private static String SQL_SCHEMA = "select table_schema, table_name, " +
            "column_name, ordinal_position from information_schema.columns " +
            "where table_schema = ? and table_name = ?";


    public static void loadMeta() {
        new Thread(() -> {
            for (; ; ) {
                if (Objects.nonNull(jdbcTemplate)) {
                    ParseTemplate.MAP.forEach((tableName, table) -> {
                        List<String> insertFields = table.getOperateTypeMap().get(
                                OperateTypeCons.ADD
                        );

                        List<String> updateFields = table.getOperateTypeMap().get(
                                OperateTypeCons.UPDATE
                        );

                        List<String> deleteFields = table.getOperateTypeMap().get(
                                OperateTypeCons.DELETE
                        );

                        LoadMeta.jdbcTemplate.query(SQL_SCHEMA,
                                ps -> {
                                    ps.setString(1, table.getDatabase());
                                    ps.setString(2, tableName);
                                },
                                (rs, i) -> {
                                    int pos = rs.getInt("ORDINAL_POSITION");
                                    String colName = rs.getString("COLUMN_NAME");

                                    if ((null != updateFields && updateFields.contains(colName))
                                            || (null != insertFields && insertFields.contains(colName))
                                            || (null != deleteFields && deleteFields.contains(colName))) {
                                        table.getPosMap().put(pos - 1, colName);
                                    }
                                    return null;
                                });
                    });
                    break;
                }
            }
        }).start();
    }
}
