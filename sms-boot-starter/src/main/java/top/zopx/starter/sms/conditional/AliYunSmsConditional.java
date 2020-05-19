package top.zopx.starter.sms.conditional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import top.zopx.starter.sms.config.SmsProperties;

import java.lang.annotation.Annotation;

/**
 * top.zopx.starter.sms.conditional.AliYunSmsConditional
 *
 * @author sanq.Yan
 * @date 2020/5/19
 */
@Slf4j
public class AliYunSmsConditional implements Condition {

    private static final String accessKeyId = SmsProperties.AliYunSmsProperties.PREFIX + ".access-key-id";
    private static final String accessKeySecret = SmsProperties.AliYunSmsProperties.PREFIX + ".access-key-secret";

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();

        log.info("{},{},{}", environment.getProperty(accessKeyId), environment.getProperty(accessKeySecret));

        return environment.containsProperty(accessKeyId)
                && environment.containsProperty(accessKeySecret);
    }
}
