package top.zopx.starter.tools.interceptors;

import org.springframework.web.method.HandlerMethod;
import top.zopx.starter.tools.constants.BusCode;
import top.zopx.starter.tools.constants.SecurityField;
import top.zopx.starter.tools.exceptions.BusException;
import top.zopx.starter.tools.tools.date.LocalDateUtils;
import top.zopx.starter.tools.tools.strings.StringUtil;
import top.zopx.starter.tools.tools.web.ParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * version: 接口拦截验证， 基于 sign 验证方式
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public abstract class SecurityInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {

            if (super.preHandle(request, response, handler)) {
                return true;
            }

            Object o = objectMap.get(SecurityField.TIMESTAMP.getName());
            if (o == null)
                throw new BusException(String.format("参数%s不存在", SecurityField.TIMESTAMP.getName()), BusCode.PARAM_CODE);

            Long timestamp = StringUtil.toLong(o);

            if (LocalDateUtils.nowTime().getTime() - timestamp >= 60 * 1000)
                throw new BusException(String.format("参数%s已过期", SecurityField.TIMESTAMP.getName()), BusCode.PARAM_CODE);

            o = objectMap.get(SecurityField.SIGN.getName());
            if (o == null)
                throw new BusException(String.format("参数%s不存在", SecurityField.SIGN.getName()), BusCode.PARAM_CODE);

            String sign = (String) o;

            String paramsSign = ParamUtil.getInstance().getSign(objectMap);
            if (!sign.equals(paramsSign)) {
                throw new BusException(String.format("参数%s验证不正确", SecurityField.SIGN.getName()), BusCode.PARAM_CODE);
            }
            return true;
        }
        return false;
    }

}
