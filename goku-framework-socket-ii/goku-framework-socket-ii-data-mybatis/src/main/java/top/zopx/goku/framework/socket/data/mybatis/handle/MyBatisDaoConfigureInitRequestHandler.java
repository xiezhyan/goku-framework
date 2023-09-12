package top.zopx.goku.framework.socket.data.mybatis.handle;

import com.google.gson.JsonElement;
import top.zopx.goku.framework.socket.tools.circuit.Context;
import top.zopx.goku.framework.socket.tools.circuit.chain.RequestHandler;
import top.zopx.goku.framework.socket.data.mybatis.MybatisDao;
import top.zopx.goku.framework.tools.util.json.GsonUtil;

import java.util.Map;

/**
 * 对Redis进行配置
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/8/20 13:32
 */
public class MyBatisDaoConfigureInitRequestHandler implements RequestHandler {

    private final Class<?> clazz;

    public MyBatisDaoConfigureInitRequestHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void handleRequest(Context context) {
        Object redis = context.attr("datasource");

        if (redis instanceof Map<?, ?> jdbcMap) {
            JsonElement jsonEle = GsonUtil.getInstance()
                    .getGson().toJsonTree(jdbcMap);
            MybatisDao.init(jsonEle.getAsJsonObject(), clazz);
            LOG.debug("。。。mybatis 初始化完成。。。");
        }
    }
}
