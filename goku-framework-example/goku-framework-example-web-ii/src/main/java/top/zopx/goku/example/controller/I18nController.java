package top.zopx.goku.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zopx.goku.framework.http.util.i18n.MessageUtil;
import top.zopx.goku.framework.tools.entity.wrapper.R;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/5/30
 */
@RestController
@RequestMapping("i18n")
public class I18nController {

//    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @GetMapping
    public R<String> test(String key) {
        return R.result(
                MessageUtil.getMessage(key)
        );
    }

}
