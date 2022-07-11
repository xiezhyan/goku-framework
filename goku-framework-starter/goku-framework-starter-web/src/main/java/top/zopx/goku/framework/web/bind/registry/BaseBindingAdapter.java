package top.zopx.goku.framework.web.bind.registry;

import org.springframework.context.SmartLifecycle;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.Resource;

/**
 * @author 俗世游子
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
    public abstract TranslateGenericConvert addtranslateGenericConvert();

    @Override
    public void start() {
        LogHelper.getLogger(BaseBindingAdapter.class).info("ResultWrapperConvert 开始注入，加入到缓存中。。。");
        bindingAdapter.convertToCache(addtranslateGenericConvert());
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
