package top.zopx.starter.step.up.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.zopx.starter.step.up.constant.StepUpType;

import java.io.File;

/**
 * top.zopx.starter.step.up.config.StepUpProperties
 *
 * @author sanq.Yan
 * @date 2020/4/28
 */
@ConfigurationProperties(prefix = "step.up")
@Configuration
@Component
@Data
public class StepUpProperties {

    private Boolean overrided = false;

    private String packageName;

    private String prefix;

    private String schema;

    private StepUpType type = StepUpType.MYBATIS;

    private String projectName;

    public String getRootPath() {
        return System.getProperty("user.dir") + File.separator + projectName;
    }
}
