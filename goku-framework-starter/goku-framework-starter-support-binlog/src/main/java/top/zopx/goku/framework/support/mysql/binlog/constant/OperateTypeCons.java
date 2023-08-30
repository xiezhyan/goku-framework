package top.zopx.goku.framework.support.mysql.binlog.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/05/25 21:23
 */
public enum OperateTypeCons {
    /**
     * 插入数据
     */
    ADD,
    /**
     * 修改数据
     */
    UPDATE,
    /**
     * 删除数据
     */
    DELETE,
    /**
     * 其他操作
     */
    OTHER;

    public static OperateTypeCons to(EventType eventType) {
        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
