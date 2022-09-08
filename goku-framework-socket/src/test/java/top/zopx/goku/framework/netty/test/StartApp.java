package top.zopx.goku.framework.netty.test;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import top.zopx.goku.framework.biz.dao.MybatisDao;
import top.zopx.goku.framework.biz.redis.RedisCache;
import top.zopx.goku.framework.netty.bind.entity.ServerAcceptor;
import top.zopx.goku.framework.netty.server.NettyServerAcceptor;
import top.zopx.goku.framework.tools.util.json.JsonUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

/**
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/7/29
 */
public class StartApp {
    public static void main(String[] args) {

        JsonObject jsonConfig = JsonUtil.getInstance().getGson().fromJson("config.json", JsonObject.class);

        // redis配置
        RedisCache.Config redisConfig = RedisCache.Config.fromJsonData(jsonConfig);
        if (Objects.nonNull(redisConfig)) {
            RedisCache.init(redisConfig);
        }
        // mybatis配置
        MybatisDao.Config mybatisConfig = MybatisDao.Config.fromJsonData(jsonConfig);
        if (Objects.nonNull(mybatisConfig)) {
            MybatisDao.init(mybatisConfig, StartApp.class);
        }

        new NettyServerAcceptor(
                ServerAcceptor.create()
                        .setFactory(new ChannelHandlerFactory())
                        .build()
        ).start();

    }

    private static JsonObject loadJsonConfig(String c) {
        if (StringUtils.isBlank(c)) {
            throw new IllegalArgumentException("传入的配置文件为空");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(c))) {
            // 获取 JSON 文本
//            String jsonText = br.lines().collect(Collectors.joining());
            // 解析 JSON 对象
            return JsonUtil.getInstance().getGson().fromJson(br, JsonObject.class);
        } catch (Exception e) {
            throw new RuntimeException("处理配置文件异常", e);
        }
    }
}
