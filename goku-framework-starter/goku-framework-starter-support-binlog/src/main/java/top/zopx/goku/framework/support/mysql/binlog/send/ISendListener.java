package top.zopx.goku.framework.support.mysql.binlog.send;

import top.zopx.goku.framework.support.mysql.binlog.entity.BinlogRowData;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/25 22:32
 */
public interface ISendListener {

    /**
     * 开启注册
     */
    void registry();

    /**
     * 需要对外发送的数据
     * @param eventData binlog Data
     */
    void onEvent(BinlogRowData eventData);

}
