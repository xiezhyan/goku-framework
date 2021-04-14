package top.zopx.square.service.impl;

import org.springframework.stereotype.Service;
import top.zopx.starter.log.service.IErrorLogService;
import top.zopx.starter.tools.tools.json.JsonUtil;

import java.util.Map;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@Service
public class ErrorLogServiceImpl implements IErrorLogService {

    @Override
    public boolean save(Map<String, Object> map) {
        System.out.println(JsonUtil.toJson(map));
        return false;
    }
}
