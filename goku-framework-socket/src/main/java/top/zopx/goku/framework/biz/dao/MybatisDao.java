package top.zopx.goku.framework.biz.dao;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.tools.exceptions.BusException;
import top.zopx.goku.framework.tools.util.json.JsonUtil;
import top.zopx.goku.framework.tools.util.reflection.PackageUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MyBatis 会话工厂类
 *
 * @author 俗世游子
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
     * @param usingConf          使用配置
     * @param scanClazzAtPackage 扫描类所在包
     */
    public static void init(Config usingConf, Class<?> scanClazzAtPackage) {
        if (null == scanClazzAtPackage) {
            throw new IllegalArgumentException("scanClazzAtPackage is null");
        }

        init(usingConf, scanClazzAtPackage.getPackage());
    }

    /**
     * 初始化
     *
     * @param usingConf   使用配置
     * @param scanPackage 扫描包
     */
    public static void init(
            Config usingConf,
            Package scanPackage) {
        init(usingConf, scanPackage.getName());
    }

    /**
     * 初始化
     *
     * @param usingConf       使用配置
     * @param scanPackageName 扫描包名称
     */
    public static void init(Config usingConf, String scanPackageName) {
        if (null == usingConf ||
                usingConf.itemMap.isEmpty()) {
            throw new BusException("usingConf 配置无效");
        }

        for (Map.Entry<String, Config.Item> entry : usingConf.itemMap.entrySet()) {
            if (null == entry.getKey() ||
                    null == entry.getValue()) {
                return;
            }

            // 获取配置项目
            final Config.Item confItem = entry.getValue();

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
                        confItem.buildPropMap()
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
                    testSession.getConnection()
                            .prepareStatement("SELECT -1")
                            .execute();

                    LOGGER.info(
                            "MySql 数据库连接成功!, serverAddr = {}:{}, db = {}, userName = {}",
                            confItem.serverHost,
                            confItem.serverPort,
                            confItem.db,
                            confItem.userName
                    );

                    SQL_SESSION_FACTORY_MAP.put(entry.getKey(), ssf);
                }
            } catch (Exception ex) {
                // 抛出运行时异常
                throw new BusException(ex.getMessage());
            }
        }
    }

    /**
     * 开启业务数据库会话
     *
     * @return MySql 会话
     */
    public static SqlSession openBizDbSession() {
        return openSession("biz");
    }

    /**
     * 开启日志数据库会话
     *
     * @return MySql 会话
     */
    public static SqlSession openLogDbSession() {
        return openSession("log");
    }

    public static SqlSession openSession(String key) {
        // Sql 会话工厂
        SqlSessionFactory ssf = SQL_SESSION_FACTORY_MAP.get(key);

        if (null == ssf) {
            throw new BusException(key + " 配置未初始化");
        }

        return ssf.openSession(true);
    }

    /**
     * 配置
     */
    public static class Config {
        public static final String JSON_KEY = "db";
        /**
         * 配置项字典
         */
        public Map<String, Item> itemMap = null;

        /**
         * 从 JSON 对象中创建配置项
         *
         * @param jsonObj JSON 对象
         * @return 配置项
         */
        public static Config fromJsonData(JsonObject jsonObj) {
            if (Objects.isNull(jsonObj) || !jsonObj.has(JSON_KEY)) {
                LOGGER.error("没有对应的配置项，无法加载");
                return null;
            }

            // MySQL 套件配置
            JsonObject dbObj = jsonObj.getAsJsonObject(JSON_KEY);

            if (null == dbObj) {
                return null;
            }

            Config newConf = new Config();
            newConf.itemMap = new ConcurrentHashMap<>(16);

            dbObj.entrySet().forEach(entry ->
                    newConf.itemMap.put(
                            entry.getKey(),
                            JsonUtil.getInstance().getGson().fromJson(entry.getValue(), Item.class)
                    )
            );

            return newConf;
        }

        /**
         * 配置项
         */
        static class Item {
            private String serverHost;
            private int serverPort;
            private String db;
            private String schema = "";
            private String userName;
            private String password;
            private int initialSize;
            private int minIdle;
            private int maxActive;
            private int maxWait;

            public String getServerHost() {
                return serverHost;
            }

            public void setServerHost(String serverHost) {
                this.serverHost = serverHost;
            }

            public int getServerPort() {
                return serverPort;
            }

            public void setServerPort(int serverPort) {
                this.serverPort = serverPort;
            }

            public String getDb() {
                return db;
            }

            public void setDb(String db) {
                this.db = db;
            }

            public String getSchema() {
                return schema;
            }

            public void setSchema(String schema) {
                this.schema = schema;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public int getInitialSize() {
                return initialSize;
            }

            public void setInitialSize(int initialSize) {
                this.initialSize = initialSize;
            }

            public int getMinIdle() {
                return minIdle;
            }

            public void setMinIdle(int minIdle) {
                this.minIdle = minIdle;
            }

            public int getMaxActive() {
                return maxActive;
            }

            public void setMaxActive(int maxActive) {
                this.maxActive = maxActive;
            }

            public int getMaxWait() {
                return maxWait;
            }

            public void setMaxWait(int maxWait) {
                this.maxWait = maxWait;
            }

            public Properties buildPropMap() {
                Properties newProp = new Properties();
                newProp.setProperty("serverHost", serverHost);
                newProp.setProperty("serverPort", String.valueOf(serverPort));
                newProp.setProperty("db", db);
                if (StringUtils.isNotBlank(schema)) {
                    schema += "&";
                }
                newProp.setProperty("schema", schema);
                newProp.setProperty("userName", userName);
                newProp.setProperty("password", password);
                newProp.setProperty("initialSize", String.valueOf(initialSize));
                newProp.setProperty("minIdle", String.valueOf(minIdle));
                newProp.setProperty("maxActive", String.valueOf(maxActive));
                newProp.setProperty("maxWait", String.valueOf(maxWait));
                return newProp;
            }
        }
    }

    /**
     * DAO
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DAO {
    }
}
