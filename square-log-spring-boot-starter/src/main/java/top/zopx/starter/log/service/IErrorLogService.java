package top.zopx.starter.log.service;

import java.util.Map;

/**
 * @author sanq.Yan
 * @date 2021/4/12
 */
public interface IErrorLogService {

    boolean save(Map<String, Object> map);

}
