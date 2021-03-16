package top.zopx.starter.tools.configurator;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * 验证框架验证
 *
 * @author sanq.Yan
 * @date 2020/1/26
 */
@Configuration
@Component
public class HibernateValidatorConfigurator {

    @Bean
    public Validator validator() {
        return getValidator();
    }

    private Validator getValidator() {

        return Validation.byProvider(HibernateValidator.class)
                .configure()
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory()
                .getValidator();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        /*
         * 设置validator模式为快速失败返回
         * */
        postProcessor.setValidator(getValidator());
        return postProcessor;
    }

}
