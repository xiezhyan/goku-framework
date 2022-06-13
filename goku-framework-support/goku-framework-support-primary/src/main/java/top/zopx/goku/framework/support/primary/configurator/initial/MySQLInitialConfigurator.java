package top.zopx.goku.framework.support.primary.configurator.initial;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import top.zopx.goku.framework.support.primary.segment.service.BusinessService;
import top.zopx.goku.framework.support.primary.configurator.marker.MySQLMarkerConfiguration;
import top.zopx.goku.framework.support.primary.core.service.IBusinessService;
import top.zopx.goku.framework.support.primary.core.service.IUpdatePlot;
import top.zopx.goku.framework.support.primary.segment.service.IDSegmentGetterService;

import javax.annotation.Resource;

/**
 * @author 俗世游子
 * @date 2021/12/25
 * @email xiezhyan@126.com
 */
public class MySQLInitialConfigurator {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private IUpdatePlot updatePlot;

    @Bean
    @ConditionalOnBean({MySQLMarkerConfiguration.MySQLPrimaryMarker.class})
    public IBusinessService businessService() {
        return new BusinessService(jdbcTemplate);
    }

    @Bean(initMethod = "init")
    @ConditionalOnBean({MySQLMarkerConfiguration.MySQLPrimaryMarker.class})
    public IDSegmentGetterService segmentGetterService(IBusinessService businessService) {
        return new IDSegmentGetterService(businessService, updatePlot, jdbcTemplate);
    }
}
