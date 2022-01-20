package top.zopx.starter.log.event.listener.api;

import top.zopx.starter.log.constant.LogConstant;
import top.zopx.starter.log.event.ApiLogEvent;
import top.zopx.starter.log.util.SpringUtil;
import top.zopx.starter.tools.tools.json.impl.FJsonUtil;
import top.zopx.starter.tools.tools.web.GlobalUtil;

import java.util.Map;

/**
 * @author sanq.Yan
 * @date 2021/4/12
 */
public class ApiPublish {

    public static void publish(Map<String, Object> map, Object result) {
        SpringUtil.addRequestInfo(GlobalUtil.Request.getRequest(), map);
        map.put(LogConstant.RESULT, FJsonUtil.INSTANCE.toJson(result));
        SpringUtil.publishEvent(new ApiLogEvent(map));
    }
}
