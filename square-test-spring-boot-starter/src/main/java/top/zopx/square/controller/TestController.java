package top.zopx.square.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zopx.square.entity.User;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author sanq.Yan
 * @date 2021/4/14
 */
@RestController
@RequestMapping("/test")
@Validated
public class TestController {

    @GetMapping("/get")
    public String get(@NotBlank(message = "name不能为空") String name) {
        return "hello:" + name;
    }

    @GetMapping("/get2")
    public String get2(@Valid User user) {
        return "hello:" + user.getName();
    }

}
