package top.zopx.starter.tools.interceptors;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.zopx.starter.tools.annotations.IgnoreSecurity;
import top.zopx.starter.tools.annotations.IgnoreTokenSecurity;
import top.zopx.starter.tools.constants.BusCode;
import top.zopx.starter.tools.constants.SecurityField;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.web.GlobalUtil;
import top.zopx.starter.tools.tools.web.ParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * version: 基础控制器
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public abstract class BaseInterceptor implements HandlerInterceptor {

    Map<String, Object> objectMap;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.OK.value());
            return true;
        }

        if (handler instanceof HandlerMethod) {

            //验证ip是否在黑名单中
            String ip = GlobalUtil.getBrowserIp(request);

            if (checkIsAllowIp(request, ip)) {
                throw new BusException(String.format("ip:%s，访问被限制， 加入黑名单", ip), BusCode.IP_CODE);
            }

            HandlerMethod hm = (HandlerMethod) handler;
            IgnoreSecurity ignoreSecurity = hm.getMethodAnnotation(IgnoreSecurity.class);

            if (ignoreSecurity != null) {
                return true;
            }

            // 先获取参数
            objectMap = getParamMap(request);

            if (objectMap == null || objectMap.isEmpty())
                throw new BusException("缺少必要参数", BusCode.PARAM_CODE);

            //是否忽略token验证
            IgnoreTokenSecurity s = hm.getMethodAnnotation(IgnoreTokenSecurity.class);

            if (s != null)
                return true;

            Object o = objectMap.get(SecurityField.TOKEN.getName());
            if (o == null)
                throw new BusException(String.format("参数%s不存在", SecurityField.TOKEN.getName()), BusCode.PARAM_CODE);

            if (!checkIsAllowToken(request, (String) o))
                throw new BusException(String.format("%s已过期，请重新登录", SecurityField.TOKEN.getName()), BusCode.TOKEN_CODE);

            return true;
        }
        throw new Exception("访问被限制");
    }


    @SneakyThrows
    private Map<String, Object> getParamMap(HttpServletRequest request) {

        switch (request.getMethod()) {
            case "GET":
                return ParamUtil.getInstance().getParam2Get(request);
            case "POST":
            case "PUT":
            case "DELETE":
                return ParamUtil.getInstance().json2Map(ParamUtil.getInstance().get());
        }

        return Collections.emptyMap();
    }

    abstract boolean checkIsAllowToken(HttpServletRequest request, String token);

    abstract boolean checkIsAllowIp(HttpServletRequest request, String ip);
}
