package top.zopx.goku.framework.cloud.configure.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import top.zopx.goku.framework.http.context.GlobalContext;
import top.zopx.goku.framework.http.util.log.LogHelper;

/**
 * @author 俗世游子
 * @date 2022/2/26 0026
 * @email xiezhyan@126.com
 */
@Configuration
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            LogHelper.getLogger(FeignInterceptor.class)
                    .debug("feignInterceptor...");

            requestTemplate.header(
                    GlobalContext.TOKEN_KEY,
                    GlobalContext.CurrentRequest.getToken()
            );
        } catch (Exception e) {
            LogHelper.getLogger(FeignInterceptor.class).error("nothing!!!");
        }
    }
}
