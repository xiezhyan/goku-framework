package top.zopx.goku.framework.mysql.binlog.send;

import top.zopx.goku.framework.mysql.binlog.entity.MySqlRowData;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/25 22:38
 */
public interface ISender {

    /**
     * 通过消息中间件将解析结果发送出去
     *
     * @param rowData binlog解析结果
     */
    void sender(MySqlRowData rowData);
}
