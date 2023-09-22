package top.zopx.goku.framework.socket.datasource;

import com.zaxxer.hikari.HikariConfig;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.socket.core.config.properties.BootstrapDatasource;
import top.zopx.goku.framework.socket.datasource.annotation.DAO;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;
import top.zopx.goku.framework.tools.util.json.GsonUtil;
import top.zopx.goku.framework.tools.util.reflection.PackageUtil;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MyBatis 会话工厂类
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/04/28
 */
public final class MybatisDao {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisDao.class);

    /**
     * MySQL 会话工厂字典
     */
    private static final Map<String, SqlSessionFactory> SQL_SESSION_FACTORY_MAP = new ConcurrentHashMap<>();

    /**
     * 私有化类默认构造器
     */
    private MybatisDao() {
    }

    /**
     * 初始化
     *
     * @param resourceMap        使用配置
     * @param scanClazzAtPackage 扫描类所在包
     */
    public static void init(Map<String, BootstrapDatasource> resourceMap, Class<?> scanClazzAtPackage) {
        if (null == scanClazzAtPackage) {
            throw new IllegalArgumentException("scanClazzAtPackage is null");
        }

        init(resourceMap, scanClazzAtPackage.getPackage());
    }

    /**
     * 初始化
     *
     * @param resourceMap 使用配置
     * @param scanPackage 扫描包
     */
    public static void init(
            Map<String, BootstrapDatasource> resourceMap,
            Package scanPackage) {
        init(resourceMap, scanPackage.getName());
    }

    /**
     * 初始化
     *
     * @param resourceMap              使用配置
     * @param scanPackageName 扫描包名称
     */
    public static void init(Map<String, BootstrapDatasource> resourceMap, String scanPackageName) {
        if (null == resourceMap ||
                resourceMap.isEmpty()) {
            throw new BusException("usingConf 配置无效", IBus.ERROR_CODE);
        }

        for (Map.Entry<String, BootstrapDatasource> entry : resourceMap.entrySet()) {
            if (null == entry.getKey() ||
                    null == entry.getValue()) {
                return;
            }

            // 获取配置项目
            BootstrapDatasource confItem = entry.getValue();

            try {
                // 找到 DAO 类
                List<Class<?>> daoClazzSet = PackageUtil.INSTANCE.getFileListBySuperClass(
                        scanPackageName,
                        true,
                        clazz -> null != clazz && null != clazz.getAnnotation(DAO.class)
                );

                // MySql 会话工厂
                final SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(
                        Resources.getResourceAsStream("mybatis.config.xml"),
                        buildProperties(confItem)
                );

                // 获取工厂配置
                Configuration mybatisConf = ssf.getConfiguration();

                for (Class<?> daoClazz : daoClazzSet) {
                    if (null != daoClazz) {
                        // 挨个注册 DAO 接口
                        mybatisConf.addMapper(daoClazz);
                    }
                }

                // 测试数据库会话
                try (SqlSession testSession = ssf.openSession()) {
                    try (PreparedStatement ps = testSession.getConnection().prepareStatement("SELECT -1")) {
                        ps.execute();
                    }

                    LOGGER.info(
                            "MySql 数据库连接成功!,key = {}, jdbc = {}, userName = {}",
                            entry.getKey(),
                            confItem.getJdbc(),
                            confItem.getUserName()
                    );

                    SQL_SESSION_FACTORY_MAP.put(entry.getKey(), ssf);
                }
            } catch (Exception ex) {
                // 抛出运行时异常
                throw new BusException(ex.getMessage(), IBus.ERROR_CODE);
            }
        }
    }

    private static Properties buildProperties(BootstrapDatasource confItem) {
        Properties newProp = new Properties();
        HikariConfig hikari = confItem.getHikari();
        hikari.setJdbcUrl(confItem.getJdbc());
        hikari.setUsername(confItem.getUserName());
        hikari.setPassword(confItem.getPassword());
        hikari.setPoolName("goku-socket-mysql");
        newProp.setProperty("hikari", GsonUtil.getInstance().toJson(hikari));
        return newProp;
    }

    public static SqlSession openSession() {
        return openSession("server");
    }

    public static SqlSession openSession(String key) {
        // Sql 会话工厂
        SqlSessionFactory ssf = SQL_SESSION_FACTORY_MAP.get(key);

        if (null == ssf) {
            throw new BusException(key + " 配置未初始化", IBus.ERROR_CODE);
        }

        return ssf.openSession(true);
    }
}
