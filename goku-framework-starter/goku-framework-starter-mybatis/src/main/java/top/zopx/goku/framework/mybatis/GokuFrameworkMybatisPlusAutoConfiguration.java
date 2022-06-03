package top.zopx.goku.framework.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zopx.goku.framework.mybatis.configurator.CusMetaObjectHandler;

/**
 * 自动注入
 *
 * @author 俗世游子
 * @date 2022/05/12
 */
@Configuration(proxyBeanMethods = false)
public class GokuFrameworkMybatisPlusAutoConfiguration {

    /**
     * 自动填充功能
     *
     * @return MetaObjectHandler
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new CusMetaObjectHandler();
    }

    /**
     * 乐观锁
     *
     * @return OptimisticLockerInnerInterceptor
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }
}
