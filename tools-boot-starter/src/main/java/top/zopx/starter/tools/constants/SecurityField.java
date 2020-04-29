package top.zopx.starter.tools.constants;

/**
 * com.sanq.product.security.enums.SecurityFieldEnum
 *
 * @author sanq.Yan
 * @date 2019/8/3
 */
public enum SecurityField {
    //需要验证的参数
    TOKEN("token"),
    TIMESTAMP("timestamp"),
    SIGN("sign"),
    ;

    private String mName;

    SecurityField(String name) {
        this.mName = name;
    }

    public String getName() {
        return mName;
    }
}
