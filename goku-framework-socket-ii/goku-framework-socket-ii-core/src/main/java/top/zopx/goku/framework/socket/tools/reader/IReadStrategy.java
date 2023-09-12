package top.zopx.goku.framework.socket.tools.reader;

import java.util.Map;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/9/12 21:51
 */
public interface IReadStrategy {

    /**
     * 读取配置文件
     * @param conf 配置文件路径
     * @return Map<String, Object>>
     */
    Map<String, Object> read(String conf);

}
