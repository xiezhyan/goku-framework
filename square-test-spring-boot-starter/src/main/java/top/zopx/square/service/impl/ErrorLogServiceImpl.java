package top.zopx.square.service.impl;

import org.springframework.stereotype.Service;
import top.zopx.starter.log.service.ILogService;
import top.zopx.starter.tools.tools.json.IJson;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 俗世游子
 * @date 2021/4/14
 */
@Service
public class ErrorLogServiceImpl implements ILogService {

    @Resource
    private IJson json;

    @Override
    public void saveError(Map<String, Object> map) {
        System.out.println("error:" + json.toJson(map));
    }

    @Override
    public void saveApi(Map<String, Object> map) {
        System.out.println("api:" + json.toJson(map));
    }
}
