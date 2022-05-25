package top.zopx.goku.framework.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import top.zopx.goku.framework.web.util.LogHelper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/05/22 8:51
 */
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@ComponentScan("top.zopx.goku.framework.web")
public class GokuFrameworkWebAutoConfiguration {

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @PostConstruct
    public void banner() {
        taskExecutor.execute(() ->
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
                                    " GoKu:       1.5.0.220601"
                    )
        );
    }

}
