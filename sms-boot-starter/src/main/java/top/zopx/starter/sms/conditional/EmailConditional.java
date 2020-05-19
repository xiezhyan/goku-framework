package top.zopx.starter.sms.conditional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * top.zopx.starter.sms.conditional.EmailConditional
 *
 * @author sanq.Yan
 * @date 2020/5/19
 */
@Slf4j
public class EmailConditional implements Condition {

    private static final String PREFIX = "spring.mail";

    private static final String host = PREFIX + ".host";
    private static final String port = PREFIX + ".port";
    private static final String username = PREFIX + ".username";
    private static final String password = PREFIX + ".password";

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();

        log.info("{},{},{},{}",
                environment.getProperty(host),
                environment.getProperty(port),
                environment.getProperty(username),
                environment.getProperty(password)
        );

        return environment.containsProperty(host)
                && environment.containsProperty(port)
                && environment.containsProperty(username)
                && environment.containsProperty(password);
    }
}
