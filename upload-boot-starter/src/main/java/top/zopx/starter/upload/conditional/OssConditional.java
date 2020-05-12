package top.zopx.starter.upload.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import top.zopx.starter.upload.config.UploadProperties;

/**
 * top.zopx.starter.upload.conditional.OssConditional
 *
 * @author sanq.Yan
 * @date 2020/5/12
 */
public class OssConditional implements Condition {

    private String endpoint = UploadProperties.OssProperties.PREFIX + ".endpoint";
    private String accessKeyId = UploadProperties.OssProperties.PREFIX + ".access-key-id";
    private String accessKeySecret = UploadProperties.OssProperties.PREFIX + ".access-key-secret";

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

        Environment environment = conditionContext.getEnvironment();

        return environment.containsProperty(endpoint)
                && environment.containsProperty(accessKeyId)
                && environment.containsProperty(accessKeySecret);
    }
}
