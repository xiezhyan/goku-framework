package top.zopx.starter.step.up.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import top.zopx.starter.step.up.utils.S;
import top.zopx.starter.tools.tools.strings.StringUtil;

/**
 * top.zopx.starter.step.up.entity.Table
 *
 * @author sanq.Yan
 * @date 2020/4/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Table {

    private String tableName;

    private String tableComment;

    private String javaName;

    public void updateJavaName(String prefix) {

        this.javaName = this.tableName;

        if (!StringUtils.isEmpty(prefix)) {
            this.javaName = this.tableName.replace(prefix, "");
        }

        int i = this.javaName.indexOf("_");

        this.javaName = i > 0 ?
                S.replaceChar(this.javaName, "_") :
                this.javaName;
    }
}
