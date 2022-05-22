package top.zopx.square.service;

import org.springframework.stereotype.Component;
import top.zopx.goku.framework.log.service.ILogService;
import top.zopx.goku.framework.tools.util.json.IJson;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 9:59
 */
@Component
public class LogServiceImpl implements ILogService {

    @Resource
    private IJson json;

    public void saveError(Map<String, Object> map) {
        LogHelper.getLogger(LogServiceImpl.class).error(json.toJson(map));
    }

    public void saveApi(Map<String, Object> map) {
        LogHelper.getLogger(LogServiceImpl.class).info(json.toJson(map));
    }
}
