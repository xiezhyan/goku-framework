package top.zopx.starter.tools.interceptors;

import org.springframework.web.method.HandlerMethod;
import top.zopx.starter.tools.annotations.AuthorityAnnotation;
import top.zopx.starter.tools.constants.BusCode;
import top.zopx.starter.tools.exceptions.BusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * version: 接口权限拦截
 * ---------------------
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
public abstract class CheckHasPermissionInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {

            if (super.preHandle(request, response, handler)) {
                return true;
            }

            HandlerMethod hm = (HandlerMethod) handler;
            AuthorityAnnotation authorityAnnotation = hm.getMethodAnnotation(AuthorityAnnotation.class);

            if (authorityAnnotation == null)
                throw new BusException("权限注解为空，无法进行权限验证： @AuthorityAnnotation");

            String[] keys = authorityAnnotation.keys();

            if (!checkIsAllowKey(request, keys)) {
                throw new BusException("无当前接口权限", BusCode.AUTH_CODE);
            }
            return true;
        }
        return false;
    }


    abstract boolean checkIsAllowKey(HttpServletRequest request,  String[] keys);
}
