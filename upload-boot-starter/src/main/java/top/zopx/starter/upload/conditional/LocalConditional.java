package top.zopx.starter.upload.conditional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import top.zopx.starter.upload.config.UploadProperties;

/**
 * top.zopx.starter.upload.conditional.LocalConditional
 *
 * @author sanq.Yan
 * @date 2020/8/29
 */
@Slf4j
public class LocalConditional implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();

        String localDir = UploadProperties.LocalProperties.PREFIX + ".local-dir";
        return environment.containsProperty(localDir);
    }
}
