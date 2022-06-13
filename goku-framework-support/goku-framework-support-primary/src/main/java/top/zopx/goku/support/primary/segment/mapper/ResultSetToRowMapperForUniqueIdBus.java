package top.zopx.goku.support.primary.segment.mapper;

import org.springframework.jdbc.core.RowMapper;
import top.zopx.goku.support.primary.segment.entity.TbSegment;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xiezhyan
 * @email xiezhyan@126.com
 * @date 2022/1/26
 */
public class ResultSetToRowMapperForUniqueIdBus implements RowMapper<TbSegment> {

    @Override
    public TbSegment mapRow(ResultSet rs, int i) throws SQLException {
        return new TbSegment(
                rs.getLong("id"),
                rs.getString("bus_key"),
                rs.getLong("max_id"),
                rs.getInt("step"),
                rs.getTime("update_time")
        );
    }
}
