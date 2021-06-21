package top.zopx.square.service.impl;

import org.springframework.stereotype.Service;
import top.zopx.starter.log.service.ILogService;
import top.zopx.starter.tools.tools.json.impl.FJsonUtil;

import java.util.Map;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@Service
public class ErrorLogServiceImpl implements ILogService {

    @Override
    public void saveError(Map<String, Object> map) {
        System.out.println("error" + FJsonUtil.INSTANCE.toJson(map));
    }

    @Override
    public void saveApi(Map<String, Object> map) {
        System.out.println("api" + FJsonUtil.INSTANCE.toJson(map));
    }
}
