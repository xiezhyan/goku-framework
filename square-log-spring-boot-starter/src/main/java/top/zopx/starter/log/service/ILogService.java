package top.zopx.starter.log.service;

import java.util.Map;

/**
 * @author sanq.Yan
 * @date 2021/4/12
 */
public interface ILogService {

    boolean saveError(Map<String, Object> map);

    boolean saveApi(Map<String, Object> map);

}
