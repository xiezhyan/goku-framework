package top.zopx.starter.tools.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * com.sanq.product.security.enums.SecurityFieldEnum
 *
 * @author sanq.Yan
 * @date 2019/8/3
 */
@Getter
@AllArgsConstructor
public enum SecurityField {
    //需要验证的参数
    TOKEN("token"),
    TIMESTAMP("timestamp"),
    SIGN("sign"),
    ;

    private String name;
}
