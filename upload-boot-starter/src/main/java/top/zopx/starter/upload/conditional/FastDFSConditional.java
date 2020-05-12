package top.zopx.starter.upload.conditional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import top.zopx.starter.upload.config.FastDfsProperties;

/**
 * top.zopx.starter.upload.conditional.FastDFSConditional
 *
 * @author sanq.Yan
 * @date 2020/5/12
 */
@Slf4j
public class FastDFSConditional implements Condition {

    private String tracker = FastDfsProperties.PREFIX + ".tracker-list";
    private String nginxHost = FastDfsProperties.NginxProperties.PREFIX + ".host";
    private String nginxPort = FastDfsProperties.NginxProperties.PREFIX + ".port";

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();

      log.info("{},{},{}", environment.containsProperty(tracker), environment.containsProperty(nginxHost), environment.containsProperty(nginxPort));
      log.info("{},{},{}", environment.getProperty(tracker));

        return environment.containsProperty(tracker)
                && environment.containsProperty(nginxHost)
                && environment.containsProperty(nginxPort);
    }
}
