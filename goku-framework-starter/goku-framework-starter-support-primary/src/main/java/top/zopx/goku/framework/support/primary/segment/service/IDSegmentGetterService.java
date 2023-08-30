package top.zopx.goku.framework.support.primary.segment.service;

import org.springframework.jdbc.core.JdbcTemplate;
import top.zopx.goku.framework.support.primary.core.service.IBusinessService;
import top.zopx.goku.framework.support.primary.core.service.IUpdatePlot;
import top.zopx.goku.framework.support.primary.core.service.impl.BaseIDSegmentGetterService;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/1/26
 */
public class IDSegmentGetterService extends BaseIDSegmentGetterService {

    private static final String CREATE_SQL = "CREATE TABLE\n" +
            "IF\n" +
            "\tNOT EXISTS `tb_segment` (\n" +
            "\t\t`id` INT NOT NULL AUTO_INCREMENT,\n" +
            "\t\t`bus_key` VARCHAR ( 40 ) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务键',\n" +
            "\t\t`max_id` BIGINT DEFAULT '0' COMMENT '最大范围',\n" +
            "\t\t`step` int default 10 comment '步长',\n" +
            "\t\t`update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
            "\tPRIMARY KEY ( `id` ) \n" +
            "\t) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '分布式ID业务表'";

    private final JdbcTemplate jdbcTemplate;

    public IDSegmentGetterService(IBusinessService businessService, IUpdatePlot updatePlot, JdbcTemplate jdbcTemplate) {
        super(businessService, updatePlot);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void init() {
        jdbcTemplate.execute(CREATE_SQL);
    }

    @Override
    public void destroy() {}
}
