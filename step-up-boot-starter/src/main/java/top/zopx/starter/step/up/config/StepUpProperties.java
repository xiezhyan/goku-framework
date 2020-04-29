package top.zopx.starter.step.up.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.zopx.starter.step.up.constant.StepUpType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * top.zopx.starter.step.up.config.StepUpProperties
 *
 * @author sanq.Yan
 * @date 2020/4/28
 */
@ConfigurationProperties(StepUpProperties.PREFIX)
@Configuration
@Component
@Data
public class StepUpProperties {

    public static final String PREFIX = "step.up";

    private String packageName;

    private String prefix;

    private String schema;

    private StepUpType type = StepUpType.MYBATIS;

    private String projectName = "";

    private Boolean overrided = false;

    private List<String> tableList = new ArrayList<>();


    public String getRootPath() {
        if (!StringUtils.isEmpty(projectName))
            return System.getProperty("user.dir") + File.separator + projectName;

        return System.getProperty("user.dir");
    }

}
