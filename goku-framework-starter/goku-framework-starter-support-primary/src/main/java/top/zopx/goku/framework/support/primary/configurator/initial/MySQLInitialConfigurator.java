package top.zopx.goku.framework.support.primary.configurator.initial;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import top.zopx.goku.framework.support.primary.configurator.marker.MySQLMarkerConfiguration;
import top.zopx.goku.framework.support.primary.core.service.IBusinessService;
import top.zopx.goku.framework.support.primary.core.service.IUpdatePlot;
import top.zopx.goku.framework.support.primary.segment.service.BusinessService;
import top.zopx.goku.framework.support.primary.segment.service.IDSegmentGetterService;

import javax.sql.DataSource;

/**
 * @author Mr.Xie
 * @date 2021/12/25
 * @email xiezhyan@126.com
 */
public class MySQLInitialConfigurator {

    @Resource
    private IUpdatePlot updatePlot;

    @Bean
    @ConditionalOnBean({MySQLMarkerConfiguration.MySQLPrimaryMarker.class})
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(DataSourceProperties properties) {
        return DataSourceBuilder.create(properties.getClassLoader())
                .type(properties.getType())
                .driverClassName(properties.determineDriverClassName())
                .url(properties.determineUrl())
                .username(properties.determineUsername())
                .password(properties.determinePassword())
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @ConditionalOnBean({MySQLMarkerConfiguration.MySQLPrimaryMarker.class})
    public IBusinessService businessService(JdbcTemplate jdbcTemplate) {
        return new BusinessService(jdbcTemplate);
    }

    @Bean(initMethod = "init")
    @ConditionalOnBean({MySQLMarkerConfiguration.MySQLPrimaryMarker.class})
    public IDSegmentGetterService segmentGetterService(IBusinessService businessService, JdbcTemplate jdbcTemplate) {
        return new IDSegmentGetterService(businessService, updatePlot, jdbcTemplate);
    }
}
