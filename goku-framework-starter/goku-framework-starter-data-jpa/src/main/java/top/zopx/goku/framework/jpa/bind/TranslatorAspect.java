package top.zopx.goku.framework.jpa.bind;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.zopx.goku.framework.http.util.binding.aspect.BaseTranslatorAspect;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/11
 */
@Aspect
@Component
@Order(2)
public class TranslatorAspect extends BaseTranslatorAspect {

    @Override
    @Pointcut("@annotation(top.zopx.goku.framework.http.util.binding.annotation.Bind) " +
            "|| @within(top.zopx.goku.framework.http.util.binding.annotation.Bind))")
    public void doPointcut() {
        // document why this method is empty
    }
}
