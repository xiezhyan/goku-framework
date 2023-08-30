package top.zopx.goku.framework.http.configurator.record;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEvent;
import top.zopx.goku.framework.http.constant.LogConstant;
import top.zopx.goku.framework.http.context.GlobalContext;
import top.zopx.goku.framework.http.util.login.UserLoginHelper;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

/**
 * @author Mr.Xie
 * @date 2021/4/12
 */
public class LogEvent extends ApplicationEvent {

    private final Map<String, Object> map;

    public LogEvent(Map<String, Object> source) {
        super(source);
        this.map = source;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public static void addRequestInfo(HttpServletRequest request, Map<String, Object> map) {
        map.put(LogConstant.REQUEST_URI, getPath(request.getRequestURI()));
        map.put(LogConstant.IP, GlobalContext.CurrentRequest.getBrowserIp());
        map.put(LogConstant.AGENT, GlobalContext.CurrentRequest.getBrowserAgent());
        map.put(LogConstant.REFERENCE, GlobalContext.CurrentRequest.getBrowserRefer());
        map.put(LogConstant.CREATE_TIME, new Date());
        map.put(LogConstant.REQUEST_TYPE, request.getMethod());
        UserLoginHelper.getUserOrNull()
                        .ifPresent(userLoginVO -> {
                            map.put(LogConstant.OPERATE_USER_ID, userLoginVO.getUserId());
                            map.put(LogConstant.OPERATE_USER_NAME, userLoginVO.getUserName());
                            map.put(LogConstant.OPERATE_TENANT_ID, userLoginVO.getTenantId());
                        });
    }

    public static String getPath(String uriStr) {
        URI uri;
        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException e) {
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }

        return uri.getPath();
    }
}
