package top.zopx.goku.framework.http.util.binding.registry;

import jakarta.annotation.Resource;
import org.springframework.context.SmartLifecycle;
import top.zopx.goku.framework.http.util.log.LogHelper;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/07/11 18:28
 */
public abstract class BaseBindingAdapter implements SmartLifecycle {

    @Resource
    private BindingAdapterFactory bindingAdapter;

    /**
     * 类型转换器
     * @return
     */
    public abstract TranslateGenericConvert addTranslateGenericConvert();

    @Override
    public void start() {
        LogHelper.getLogger(BaseBindingAdapter.class).debug("ResultWrapperConvert 开始注入，加入到缓存中。。。");
        bindingAdapter.convertToCache(addTranslateGenericConvert());
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
