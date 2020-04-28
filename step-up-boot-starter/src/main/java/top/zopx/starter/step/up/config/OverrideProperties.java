package top.zopx.starter.step.up.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * top.zopx.starter.step.up.config.OverrideProperties
 *
 * @author sanq.Yan
 * @date 2020/4/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverrideProperties {

    private Boolean overrided = false;

    private List<String> tableList = new ArrayList<>();

}
