package top.zopx.goku.framework.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.PostConstruct;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:51
 */
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@ComponentScan("top.zopx.goku.framework.web")
public class GokuFrameworkWebAutoConfiguration {

    @PostConstruct
    public void banner() {
        LogHelper.getLogger(getClass())
                .info(
                        "\n{}",
                        "      _,---.     _,.---._    ,--.-.,-.\n" +
                                "  _.='.'-,  \\  ,-.' , -  `. /==/- |\\  \\ .--.-. .-.-.\n" +
                                " /==.'-     / /==/_,  ,  - \\|==|_ `/_ //==/ -|/=/  |\n" +
                                "/==/ -   .-' |==|   .=.     |==| ,   / |==| ,||=| -|\n" +
                                "|==|_   /_,-.|==|_ : ;=:  - |==|-  .|  |==|- | =/  |\n" +
                                "|==|  , \\_.' )==| , '='     |==| _ , \\ |==|,  \\/ - |\n" +
                                "\\==\\-  ,    ( \\==\\ -    ,_ //==/  '\\  ||==|-   ,   /\n" +
                                " /==/ _  ,  /  '.='. -   .' \\==\\ /\\=\\.'/==/ , _  .'\n" +
                                " `--`------'     `--`--''    `--`      `--`..---'\n" +
                                " GoKu:       1.5.1.1231"
                );
    }

}
