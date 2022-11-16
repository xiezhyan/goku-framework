package top.zopx.goku.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zopx.goku.framework.tools.entity.wrapper.R;

/**
 * @author 谢先生
 * @email xiezhyan@126.com
 * @date 2022/11/14 17:58
 */
@SpringBootApplication
@RestController
@RequestMapping
public class ExampleApp {
    @GetMapping
    public R<String> index() {
        return R.result("index...");
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleApp.class);
    }
}
