package top.zopx.goku.example.mysql.binlog;


import org.springframework.stereotype.Component;
import top.zopx.goku.framework.support.mysql.binlog.entity.MySqlRowData;
import top.zopx.goku.framework.support.mysql.binlog.send.ISender;
import top.zopx.goku.framework.tools.util.json.JsonUtil;
import top.zopx.goku.framework.web.util.LogHelper;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 17:58
 */
@Component
public class SendKafkaImpl implements ISender {

    @Override
    public void sender(MySqlRowData rowData) {
        LogHelper.getLogger(ISender.class)
                .info(
                        "rowData = {}",
                        JsonUtil.getInstance().toJson(rowData)
                );

    }
}
