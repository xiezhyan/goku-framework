package top.zopx.goku.framework.redis.bus.code;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/6/7 21:07
 */
public class CodeVO implements Serializable {

    /**
     * 画布等页面展示文本
     */
    private String imgText;
    /**
     * 最终存储在redis中用来校验
     */
    private String resultCode;

    public CodeVO(String imgText, String resultCode) {
        this.imgText = StringUtils.isBlank(imgText) ? resultCode : imgText;
        this.resultCode = resultCode;
    }

    public String getImgText() {
        return imgText;
    }

    public void setImgText(String imgText) {
        this.imgText = imgText;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
