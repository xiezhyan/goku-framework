package top.zopx.goku.framework.socket.biz;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.datasource.DataSourceFactory;
import top.zopx.goku.framework.tools.exceptions.BusException;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 自定义数据源工厂类
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public class CustomDataSourceFactory implements DataSourceFactory {
    /**
     * 属性字典
     */
    private final Map<Object, Object> propMap = new HashMap<>();

    /**
     * 数据源
     */
    private DataSource dataSource = null;

    @Override
    public void setProperties(Properties props) {
        if (null != props) {
            this.propMap.putAll(props);
        }
    }

    @Override
    public DataSource getDataSource() {
        if (null == this.dataSource) {
            synchronized (CustomDataSourceFactory.class) {
                if (null == this.dataSource) {
                    this.dataSource = this.createDataSource();
                }
            }
        }

        return this.dataSource;
    }

    /**
     * 创建数据源
     *
     * @return 数据源
     */
    private DataSource createDataSource() {
        try {
            return DruidDataSourceFactory.createDataSource(this.propMap);
        } catch (Exception ex) {
            throw new BusException("初始化数据源失败");
        }
    }
}
