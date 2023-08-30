package top.zopx.goku.framework.support.primary.segment.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import top.zopx.goku.framework.support.primary.core.entity.Business;
import top.zopx.goku.framework.support.primary.core.service.IBusinessService;
import top.zopx.goku.framework.support.primary.segment.entity.TbSegment;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/1/26
 */
@SuppressWarnings("all")
public class BusinessService implements IBusinessService {

    public static final String TABLE_NAME = "tb_segment";
    public static final int MAX_RETRY = 3;

    private final JdbcTemplate jdbcTemplate;

    public BusinessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public synchronized Business getBusinessByKey(String key, int pullSize) {
        Business business = new Business();
        business.setTag(key);

        long newMaxId = pullSize;
        int update = 0;

        try {
            TbSegment tbSegment =
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM " + TABLE_NAME + " WHERE bus_key = ? ORDER BY max_id DESC LIMIT 1",
                            new ResultSetToRowMapperForUniqueIdBus(),
                            key);

            // 更新当前key
            business.setCurrentID(tbSegment.getMaxID());
            newMaxId = tbSegment.getMaxID() + pullSize;
            int i = 0;
            while (i++ < 3 && 0 == update) {
                update = jdbcTemplate.update(getUpdatePreparedStatementCreator(key, newMaxId));
            }
        } catch (Exception e) {
            if (e instanceof EmptyResultDataAccessException) {
                // 插入当前key
                int i = 0;
                while (i++ < 3 && 0 == update) {
                    update = jdbcTemplate.update(getInsertPreparedStatementCreator(key, newMaxId, pullSize));
                }
                business.setCurrentID(1L);
            } else {
                throw new BusException(e.getMessage(), IBus.ERROR_CODE);
            }
        }

        if (0 == update) {
            throw new BusException(MessageFormat.format("业务ID操作出现异常, 标识key={0}", key), IBus.ERROR_CODE);
        }

        business.setMaxID(newMaxId);
        return business;
    }

    private PreparedStatementCreator getUpdatePreparedStatementCreator(String key, long newMaxId) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psmt = connection.prepareStatement("update " + TABLE_NAME + " set max_id = ? where bus_key = ?");
                psmt.setLong(1, newMaxId);
                psmt.setString(2, key);
                return psmt;
            }
        };
    }

    private PreparedStatementCreator getInsertPreparedStatementCreator(String key, long newMaxId, int pullSize) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psmt = connection.prepareStatement("insert into " + TABLE_NAME + "(bus_key, max_id, step) values(?,?,?)");
                psmt.setString(1, key);
                psmt.setLong(2, newMaxId);
                psmt.setInt(3, pullSize);
                return psmt;
            }
        };
    }

    public static class ResultSetToRowMapperForUniqueIdBus implements RowMapper<TbSegment> {

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

}
