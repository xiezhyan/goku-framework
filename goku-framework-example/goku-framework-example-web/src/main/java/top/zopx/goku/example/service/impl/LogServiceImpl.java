package top.zopx.goku.example.service.impl;

import org.springframework.stereotype.Service;
import top.zopx.goku.framework.log.service.ILogService;

import java.util.Map;

/**
 * 日志
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022-08-12 16:56:52
 */
@Service("logService")
public class LogServiceImpl implements ILogService {

    @Override
    public void saveError(Map<String, Object> map) {

    }

    @Override
    public void saveApi(Map<String, Object> map) {

    }
}

