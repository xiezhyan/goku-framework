package top.zopx.goku.framework.socket.data.mybatis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 自定义数据源工厂类
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public class CustomDataSourceFactory implements DataSourceFactory {
    /**
     * 属性字典
     */
    private final Properties props = new Properties();

    /**
     * 数据源
     */
    private DataSource dataSource = null;

    @Override
    public void setProperties(Properties props) {
        if (null != props) {
            this.props.putAll(props);
        }
    }

    @Override
    @SuppressWarnings("all")
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
            String hikariStr = this.props.get("hikari").toString();

            HikariConfig config = GsonUtil.getInstance().toObject(hikariStr, HikariConfig.class);

            return new HikariDataSource(config);
        } catch (Exception ex) {
            throw new BusException(ex.getMessage(), IBus.ERROR_CODE);
        }
    }
}
